package edu.mentoring.payclip.design.pattern.observer.handler;

import edu.mentoring.payclip.design.pattern.observer.domain.Observer;
import edu.mentoring.payclip.design.pattern.observer.domain.TopicName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SubjectManager {

    private final Map<TopicName, List<Observer>> observers;

    public SubjectManager(Map<TopicName, List<Observer>> observers) {
        this.observers = observers;
    }

    public void subscribe(TopicName topic, Observer observer) {
        List<Observer> subscribers = this.observers.get(topic);
        subscribers.add(observer);
    }

    public void unsubscribe(TopicName topic, Observer observer) {
        List<Observer> subscribers = this.observers.get(topic);
        subscribers.remove(observer);
    }

    public <T> void notify(TopicName topic, String data) {
        List<Observer> subscribers = this.observers.get(topic);

        subscribers.forEach(subscriber -> {
            subscriber.update(data);
        });
    }

}
