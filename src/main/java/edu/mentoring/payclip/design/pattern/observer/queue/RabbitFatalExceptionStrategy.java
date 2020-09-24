package edu.mentoring.payclip.design.pattern.observer.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

@Slf4j
public class RabbitFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    @Override
    public boolean isFatal(Throwable t) {
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException failedException = (ListenerExecutionFailedException) t;
            String queue = failedException.getFailedMessage().getMessageProperties().getConsumerQueue();

            Message failedMessage = failedException.getFailedMessage();
            log.error("Failed to process queue [{}] with message {}", queue, failedMessage);
        }

        return super.isFatal(t);
    }
}
