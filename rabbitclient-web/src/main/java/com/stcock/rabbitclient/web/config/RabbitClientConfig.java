package com.stcock.rabbitclient.web.config;

import com.stcock.rabbitclient.service.MessageReceiver;
import com.stcock.rabbitclient.service.RabbitStockServiceGateway;
import com.stcock.rabbitclient.service.StockServiceGateway;
import com.stcock.rabbitclient.service.model.Quote;
import com.stcock.rabbitclient.service.model.Stock;
import com.stcock.rabbitclient.service.model.StockExchange;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitClientConfig extends AbstractStockAppRabbitConfiguration {

    @Value("${stocks.quote.pattern}")
    private String marketDataRoutingKey;

    @Override
    protected void configureRabbitTemplate(RabbitTemplate template) {
        template.setRoutingKey(STOCK_REQUEST_QUEUE_NAME);
        template.setMandatory(true);
        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

            }
        });
    }


    @Bean
    Queue marketDataQueue() {
        return amqpAdmin().declareQueue();
    }

    @Bean
    Binding marketDataBinding() {
        return BindingBuilder.bind(marketDataQueue())
                .to(marketDataExchange())
                .with(marketDataRoutingKey);
    }

    @Bean
    DirectMessageListenerContainer directContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        DirectMessageListenerContainer directMessageListenerContainer = new DirectMessageListenerContainer();
        directMessageListenerContainer.setConnectionFactory(connectionFactory);
        directMessageListenerContainer.setQueues(marketDataQueue());
        directMessageListenerContainer.setMessageListener(listenerAdapter);
        directMessageListenerContainer.setConsumersPerQueue(3);
        return directMessageListenerContainer;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver){
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);

        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        //消费端配置映射
        Map<String, Class<?>> idClassMapper  = new HashMap<>();
        idClassMapper.put("com.stock.model.Quote", Quote.class);
        idClassMapper.put("com.stock.model.Stock", Stock.class);
        idClassMapper.put("com.stock.model.StockExchange", StockExchange.class);

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setIdClassMapping(idClassMapper);
        jsonMessageConverter.setJavaTypeMapper(typeMapper);

        adapter.setMessageConverter(jsonMessageConverter);

        //adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        adapter.setDefaultListenerMethod("receiveMessage");
        return adapter;
    }

    @Bean
    public Queue traderJoeQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    StockServiceGateway stockServiceGateway() {
        RabbitStockServiceGateway serviceGateway = new RabbitStockServiceGateway();
        serviceGateway.setDefaultReplyTo(traderJoeQueue().getName());
        return serviceGateway;
    }
}
