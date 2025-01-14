package com.okoyo.mpesasimulator.mpesasimulator.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String MSIM_EXPRESS_QUEUE = "MsimExpressQueue";
    public static final String MSIM_CALLBACK_QUEUE = "MsimCallbackQueue";
    public static final String TRANSACTION_EXCHANGE = "TransactionExchange";
    public static final String CALLBACK_EXCHANGE = "CallbackExchange";
    public static final String MSIM_EXPRESS_QUEUE_ROUTING_KEY = "ExpressQueueRoutingKey";
    public static final String MSIM_EXPRESS_CALLBACK_QUEUE_ROUTING_KEY = "CallbackQueueRoutingKey";


    @Bean
    public Queue msimExpressQueue() {
        // Defines a queue named "MsimExpressQueue"
        return new Queue(MSIM_EXPRESS_QUEUE, false); // Non-durable queue
    }

    @Bean
    public Queue msimExpressCallbackQueue() {
        // Defines a queue named "MsimCallbackQueue"
        return new Queue(MSIM_CALLBACK_QUEUE, false); // Non-durable queue
    }

    @Bean
    public TopicExchange transactionExchange() {
        // Defines an exchange named "TransactionExchange"
        return new TopicExchange(TRANSACTION_EXCHANGE);
    }

    @Bean
    public TopicExchange callbackExchange() {
        return new TopicExchange(CALLBACK_EXCHANGE);
    }

    @Bean
    public Binding msimExpressBinding(Queue msimExpressQueue, TopicExchange transactionExchange) {
        // Binds the "MsimExpressQueue" queue to the exchange with a routing key
        return BindingBuilder.bind(msimExpressQueue).to(transactionExchange).with(MSIM_EXPRESS_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Binding msimExpressCallbackBinding(Queue msimExpressCallbackQueue, TopicExchange callbackExchange) {
        // Binds the "MsimCallbackQueue" queue to the exchange with a routing key
        return BindingBuilder.bind(msimExpressCallbackQueue).to(callbackExchange).with(MSIM_EXPRESS_CALLBACK_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        // Configures a RabbitTemplate to send/receive messages
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
