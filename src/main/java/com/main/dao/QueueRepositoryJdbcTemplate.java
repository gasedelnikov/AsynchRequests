package com.main.dao;

import com.main.model.Queue;
import com.main.model.QueueRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class QueueRepositoryJdbcTemplate {
  
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Queue findByIdQueue(int uuid){
	String sql = "SELECT UUID, TYPE, BODY, CREATED_DATE "
                    + " FROM QUEUE "
                   + " WHERE UUID = ?";
        Queue queue = null;
        try {
            queue = (Queue)jdbcTemplate.queryForObject(
                            sql, new Object[]{uuid}, new QueueRowMapper());        
        }
        catch(DataAccessException ex){
        }
	return queue;
    }    
    
    public void saveQueue(Queue queue){
        String sql = "INSERT INTO QUEUE (UUID, TYPE,  BODY,  CREATED_DATE)"
                            + " VALUES (?,?, ?, ?)";
	jdbcTemplate.update(sql, queue.getUuid()
                                ,queue.getQueueType()
                                ,queue.getBody()
                                ,new java.sql.Timestamp(queue.getCreated_date())       
        );             
    }  
}
