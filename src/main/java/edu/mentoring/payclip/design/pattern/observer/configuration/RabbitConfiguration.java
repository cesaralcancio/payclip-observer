package edu.mentoring.payclip.design.pattern.observer.configuration;

import com.payclip.financial.service.common.util.gson.Gson2JsonMessageConverter;
import com.payclip.financial.service.common.util.gson.GsonMessageConverter;
import edu.mentoring.payclip.design.pattern.observer.queue.RabbitFatalExceptionStrategy;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer {

    public static final String queueName = "edu.mentoring.payclip.design.pattern.observer";
    static final String topicExchangeName = "edu.mentoring.exchange.observer";
    static final String deadLetterRoutingKey = queueName + ".expired";

    @Value("${spring.rabbitmq.template.reply-timeout:60000}")
    private Integer templateReplyTimeout;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler());

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(gson2JsonMessageConverter());
        rabbitTemplate.setReplyTimeout(templateReplyTimeout);
        return rabbitTemplate;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new GsonMessageConverter());

        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    private Gson2JsonMessageConverter gson2JsonMessageConverter() {
        return new Gson2JsonMessageConverter();
    }


    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new RabbitFatalExceptionStrategy());
    }

    private Queue createQueue(String queueName, String deadLetterRoutingKey, Integer messageTtlQueue) {
        return QueueBuilder
                .durable(queueName)
                .withArgument("x-dead-letter-exchange", topicExchangeName) // The default exchange
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey) // Route to the queue when the TTL occurs
                .withArgument("x-message-ttl", messageTtlQueue)
                .build();
    }

    @Bean
    public Queue observerQueue() {
        return createQueue(queueName, deadLetterRoutingKey, templateReplyTimeout);
    }

}
