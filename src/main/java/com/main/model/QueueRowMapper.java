package com.main.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author User
 */
public class QueueRowMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Queue queue = QueueFactory.getQueue(
              rs.getString("TYPE") 
            , rs.getString("UUID")
            , rs.getString("BODY")
            , rs.getDate("CREATED_DATE").getTime()
        );
        return queue;
    }
    
}

