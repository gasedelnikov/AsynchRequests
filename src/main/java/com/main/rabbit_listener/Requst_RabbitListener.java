package com.main.rabbit_listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.RabbitConfiguration;
import com.main.dao.QueueRepository;
import com.main.model.Queue;
import com.main.model.QueueFactory;
import com.main.model.QueueLookup;
import com.main.model.QueueValidate;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Requst_RabbitListener {
    Logger logger = Logger.getLogger(Requst_RabbitListener.class);

    @Autowired
    QueueRepository queueRepository;     
    
    @RabbitListener(containerFactory = "containerFactory", queues = RabbitConfiguration.MQ_QUEUE_REQUST_VALIDATE)
    public void worker_validate(String message) {
        logger.info("accepted on worker VALIDATE: " + message);
        execute(QueueValidate.QUEUE_TYPE, message);
    }

    @RabbitListener(containerFactory = "containerFactory", queues = RabbitConfiguration.MQ_QUEUE_REQUST_LOOKUP)
    public void worker_lookup(String message) {
        logger.info("accepted on worker LOOKUP: " + message);
        execute(QueueLookup.QUEUE_TYPE, message);
    }
    
    private void execute(String queue_type, String requestBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(requestBody);

            int delay         = root.path("delay").asInt(); 
	    String uuid       = root.path("uuid").asText();         
	    String body       = root.path("body").asText();    
	    long created_date = root.path("created_date").asLong();
            
            Queue queue = QueueFactory.getQueue(queue_type, uuid, body, created_date); 
            queue.setDelay(delay);
            queueRepository.saveQueue(queue);
            queue.execute(delay);
        } catch (IOException ex) {
            logger.info(queue_type + "; " + requestBody);            
        }            
    }    
    
}
