package com.main.model;

import com.main.dao.QueueRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Queue implements QueueInterface{
    
//    private static final AtomicInteger GLOBAL_ID_QUEUE = new AtomicInteger(0);
    public static final List<String> QUEUE_TYPES  = Arrays.asList((new String[]{ 
                                     QueueLookup.QUEUE_TYPE
                                   , QueueValidate.QUEUE_TYPE}));

   
    protected String uuid;
    protected String body;
    protected long created_date;
    protected int delay;    
    
    public Queue(String body) {
        this(   UUID.randomUUID().toString()
              , body
              , new java.util.Date().getTime()
        ); 
    }
    
    public Queue(String uuid, String body, long created_date) {
        this.uuid = uuid;
        this.body = body;
        this.created_date = created_date;
    }    

    @Override
    public String toString() {
        return "Queue{" +
                "uuid=" + uuid +
                ", body='" + body + '\'' +
                ", created_date=" + created_date +
                '}';
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public long getCreated_date() {
        return created_date;
    }
    
    @Override
    public abstract void execute(int delay) ;
    
    @Override
    public abstract String getQueueType(); 

    @Override
    public int getDelay() {
        return delay;
    }
    
    @Override
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    
}
