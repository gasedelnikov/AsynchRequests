package com.main.rabbit_listener;

import com.main.RabbitConfiguration;
import com.main.dao.QueueRepository;
import com.main.model.Queue;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Rpc_RabbitListener {
    Logger logger = Logger.getLogger(Rpc_RabbitListener.class);

    @Autowired
    QueueRepository queueRepository;     
    
    @RabbitListener(queues = RabbitConfiguration.MQ_QUEUE_RPC)
    public String worker_RPC(int id) throws InterruptedException {
        logger.info("Received on worker E6: " + id);
        
        Queue queue = queueRepository.findByIdQueue(id);
        if (queue != null){
            return queue.toString();
        }          
        else{
            return "in prosess";
        }
    }
}
