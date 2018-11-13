package com.main.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.main.RabbitConfiguration;
import com.main.model.Queue;
import com.main.model.QueueFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class Rabbit_Controller {
    Logger logger = Logger.getLogger(Rabbit_Controller.class);

    @Autowired
    private RabbitTemplate template;    
    
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    @ResponseBody
    String submitRequest(HttpServletRequest request, @RequestBody String requestBody) {
        logger.info("Submit request " + requestBody);
    
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(requestBody);
	    String queue_type = root.path("queue_type").asText(); 
	    String delay = root.path("delay").asText();             
//String num = root.path("number").asText();           

            if (queue_type != null && Queue.QUEUE_TYPES.contains(queue_type) ){
                Queue queue = QueueFactory.getQueue(queue_type, requestBody);
                map.put("status", "submit");
                map.put("delay", delay);
                map.put("uuid", queue.getUuid());
                map.put("body", queue.getBody());
                map.put("created_date", queue.getCreated_date());
                
                String emitText = mapper.writeValueAsString(map);
                emitRequest(queue_type, emitText);        
                logger.info("request Submit " + emitText);
            } 
            else{
                map.put("status", "error");
                map.put("message", "error queue type");
                map.put("body", requestBody);                
            }
        } catch (IOException ex) {
            map.put("status", "error");
            map.put("message", "error JSON parsing");
            map.put("body", requestBody);   
        }     
        
        String result = null;
        try {
            result = mapper.writeValueAsString(map);
        } catch (JsonProcessingException ex) {
        }   

        return result;
    }
    
    @RequestMapping("/rpc/{id}")
    @ResponseBody
    String submitRPC(@PathVariable("id") int id) {
        logger.info(String.format("Emit id ='%s'", id));

//        Queue queue = queueRepository.findByIdQueue(id);
        Object response = (String) template.convertSendAndReceive(RabbitConfiguration.MQ_EXCHANGE_RPC, RabbitConfiguration.MQ_QUEUE_RPC, id);
        logger.info(String.format("Received on producer '%s'", response));        
        
        return String.valueOf("returned from worker : " + response);
    }    
   
    public void emitRequest(String rkey, Object object) {
        logger.info("Emit to " + RabbitConfiguration.MQ_EXCHANGE_REQUST+ "; rkey = "+ rkey);
        template.setExchange(RabbitConfiguration.MQ_EXCHANGE_REQUST);
        template.convertAndSend(rkey, object);        
    }    
    
    
}