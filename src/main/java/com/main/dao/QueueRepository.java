package com.main.dao;

import com.main.model.Queue;
import com.main.model.QueueRowMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Repository
public class QueueRepository {
    Logger logger = Logger.getLogger(QueueRepository.class);
    
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;    
    
    public Queue findByIdQueue(int uuid){
	String sql = "SELECT UUID, TYPE, BODY, CREATED_DATE "
                    + " FROM QUEUE "
                   + " WHERE UUID = :UUID";
        Map<String, Object> parameters = new HashMap<>();        
        parameters.put("UUID", uuid);        
        
        Queue queue = null;
        try {
            queue = (Queue)namedJdbcTemplate.queryForObject(
                            sql, parameters, new QueueRowMapper());        
        }
        catch(DataAccessException ex){
        }
	return queue;
    }    
    
    public void delay(int delay, String uuid){
        logger.info("start: " + uuid);
        
	String sql = "SELECT "+delay+" as delay, '"+uuid+"' as uuid, pg_sleep(:delay)";
        Map<String, Object> parameters = new HashMap<>();        
        parameters.put("delay", delay);        
        
        try {
            namedJdbcTemplate.queryForObject(sql, parameters, Integer.class);        
        }
        catch(DataAccessException ex){
        }
        logger.info("stop: " + uuid);        
    }       
    
    public void saveQueue(Queue queue){
        String sql = "INSERT INTO QUEUE (UUID,  TYPE,  BODY,  CREATED_DATE)"
                  + " SELECT :UUID, :TYPE, :BODY, :CREATED_DATE"
                   + " WHERE NOT EXISTS ("
                         + " SELECT null FROM QUEUE B WHERE B.UUID = :UUID)";
			
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("UUID", queue.getUuid());
        parameters.put("TYPE", queue.getQueueType());
        parameters.put("BODY", queue.getBody());
        parameters.put("CREATED_DATE", new java.sql.Timestamp(queue.getCreated_date()));

        delay(queue.getDelay(), queue.getUuid()); 
	namedJdbcTemplate.update(sql, parameters);             
    }  

}
