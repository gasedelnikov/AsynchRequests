package com.main;

import com.main.model.QueueLookup;
import com.main.model.QueueValidate;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {
    public final static String MQ_EXCHANGE_RPC = "exchangeRPC";    
    public final static String MQ_QUEUE_RPC = "queue_rpc";  
    public final static String MQ_EXCHANGE_REQUST = "exchangeRequest";
    public final static String MQ_QUEUE_REQUST_VALIDATE = "queue_request_validate";      
    public final static String MQ_QUEUE_REQUST_LOOKUP = "queue_request_lookup";    
    public final static String MQ_QUEUE_RKEY_VALIDATE = QueueValidate.QUEUE_TYPE;      
    public final static String MQ_QUEUE_RKEY_LOOKUP = QueueLookup.QUEUE_TYPE;     
    
    @Value("${factory.concurrent-consumers}")
    int concurrentConsumers = 1;    

    @Value("${factory.prefetch-count}")
    int prefetchCount = 1;    
   
    Logger logger = Logger.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setPrefetchCount(prefetchCount);
        return factory;
    }    
    
    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
    
    @Bean
    public FanoutExchange exchangeRPC(){
        return new FanoutExchange(RabbitConfiguration.MQ_EXCHANGE_RPC);
    }     

    @Bean
    public Queue myQueueRPC() {
        return new Queue(RabbitConfiguration.MQ_QUEUE_RPC);
    }   
    
    @Bean
    public Binding bindingRPC(){
        return BindingBuilder.bind(myQueueRPC()).to(exchangeRPC());
    }    
    
    @Bean
    public DirectExchange exchangeRequst(){
        return new DirectExchange(RabbitConfiguration.MQ_EXCHANGE_REQUST);
    }    
    
    @Bean
    public Queue myQueueRequstValidate() {
        return new Queue(RabbitConfiguration.MQ_QUEUE_REQUST_VALIDATE);
    }

    @Bean
    public Queue myQueueRequstLookup() {
        return new Queue(RabbitConfiguration.MQ_QUEUE_REQUST_LOOKUP);
    }

    @Bean
    public Binding bindingLookup(){
        return BindingBuilder.bind(myQueueRequstLookup())
            .to(exchangeRequst())
            .with(RabbitConfiguration.MQ_QUEUE_RKEY_LOOKUP);     
    }

    @Bean
    public Binding bindingValidate(){
        return BindingBuilder.bind(myQueueRequstValidate())
            .to(exchangeRequst())
            .with(RabbitConfiguration.MQ_QUEUE_RKEY_VALIDATE);   
    }  
}