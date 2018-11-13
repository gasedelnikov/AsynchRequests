package com.main.model;

import java.util.Date;

/**
 *
 * @author User
 */
public class QueueFactory {
    
    public static Queue getQueue(String type, String body){
        Queue result;
        switch (type){
            case QueueValidate.QUEUE_TYPE:{
                result =new QueueValidate(body);
                break;
            }
            case QueueLookup.QUEUE_TYPE:{
                result =new QueueLookup(body);
                break;
            }
            default: {
                result = null;
            }
        }  
        return result;
    }   
    
    public static Queue getQueue(String type, String uuid, String body, long created_date){
        Queue result;
        switch (type){
            case QueueValidate.QUEUE_TYPE:{
                result = new QueueValidate(uuid, body, created_date);
                break;
            }
            case QueueLookup.QUEUE_TYPE:{
                result = new QueueLookup(uuid, body, created_date);
                break;
            }
            default: {
                result = null;
            }
        }  
        return result;
    }    
    
}
