package edu.mentoring.payclip.design.pattern.observer.queue;

import com.google.gson.Gson;
import edu.mentoring.payclip.design.pattern.observer.configuration.RabbitConfiguration;
import edu.mentoring.payclip.design.pattern.observer.domain.request.EventRequest;
import edu.mentoring.payclip.design.pattern.observer.handler.SubjectManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObserverDemoListener {

    private final Gson gson;
    private final SubjectManager subjectManager;

    public ObserverDemoListener(Gson gson, SubjectManager subjectManager) {
        this.gson = gson;
        this.subjectManager = subjectManager;
    }

    @RabbitListener(queues = RabbitConfiguration.queueName)
    public void handle(Message messge) {
        String messageString = new String(messge.getBody());
        log.info("Message: {}", messageString);

        EventRequest request = gson.fromJson(messageString, EventRequest.class);
        subjectManager.notify(request.getTopic(), request.getData());
    }

}

