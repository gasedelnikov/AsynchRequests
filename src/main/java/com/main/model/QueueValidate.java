package com.main.model;

import com.main.dao.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QueueValidate extends Queue implements QueueInterface{
    public static final String QUEUE_TYPE = "queue_type_validate";
    
//    @Autowired
//    QueueRepository queueRepository;     
    
    public QueueValidate(String body) {
        super(body); 
    }
    
    public QueueValidate(String uuid, String body, long created_date) {
        super(uuid, body, created_date);
    }    

    @Override
    public void execute(int delay) {
        
//        queueRepository.delay(delay, uuid);
        
//        try {
//            Thread.sleep(delay);
//        } catch (InterruptedException ex) {
//        }           
    }
    
    @Override
    public String getQueueType() {
        return QueueValidate.QUEUE_TYPE;
    }    
}
