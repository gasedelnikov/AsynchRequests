package com.main.model;

public interface QueueInterface {
    
    public String getUuid();
    public String getBody();
    public String getQueueType();    
    public long getCreated_date() ;
    public void execute(int delay);

    public int getDelay();
    public void setDelay(int delay);  
}
