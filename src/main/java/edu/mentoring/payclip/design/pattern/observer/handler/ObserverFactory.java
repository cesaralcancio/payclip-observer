package edu.mentoring.payclip.design.pattern.observer.handler;

import edu.mentoring.payclip.design.pattern.observer.domain.EventName;
import edu.mentoring.payclip.design.pattern.observer.domain.TopicName;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ObserverFactory {

    private final SubjectManager subjectManager;
    private EventObserverAdapter eventObserverAdapter;

    public ObserverFactory(SubjectManager subjectManager, EventObserverAdapter eventObserverAdapter) {
        this.subjectManager = subjectManager;
        this.eventObserverAdapter = eventObserverAdapter;
    }

    @Bean
    public void helloSubscription() {
        subjectManager.subscribe(TopicName.HELLO_OBSERVER_PATTERN, eventObserverAdapter.instance(EventName.EMAIL));
        subjectManager.subscribe(TopicName.HELLO_OBSERVER_PATTERN, eventObserverAdapter.instance(EventName.LOGGING));
    }

    @Bean
    public void newOrderSubscription() {
        subjectManager.subscribe(TopicName.NEW_ORDER, eventObserverAdapter.instance(EventName.LOGGING));
        subjectManager.subscribe(TopicName.NEW_ORDER, eventObserverAdapter.instance(EventName.SLACK));
    }
}
