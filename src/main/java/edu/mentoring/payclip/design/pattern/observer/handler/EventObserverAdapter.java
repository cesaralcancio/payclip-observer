package edu.mentoring.payclip.design.pattern.observer.handler;

import edu.mentoring.payclip.design.pattern.observer.domain.EventName;
import edu.mentoring.payclip.design.pattern.observer.domain.Observer;
import edu.mentoring.payclip.design.pattern.observer.handler.observers.EmailObserver;
import edu.mentoring.payclip.design.pattern.observer.handler.observers.LoggingObserver;
import edu.mentoring.payclip.design.pattern.observer.handler.observers.SlackObserver;
import org.springframework.stereotype.Component;

@Component
public class EventObserverAdapter {

    private final SlackObserver slackObserver;
    private final LoggingObserver loggingObserver;
    private final EmailObserver emailObserver;

    public EventObserverAdapter(SlackObserver slackObserver, LoggingObserver loggingObserver, EmailObserver emailObserver) {
        this.slackObserver = slackObserver;
        this.loggingObserver = loggingObserver;
        this.emailObserver = emailObserver;
    }

    public Observer instance(EventName event) {
        switch (event) {
            case EMAIL:
                return emailObserver;
            case SLACK:
                return slackObserver;
            case LOGGING:
                return loggingObserver;
            default:
                throw new IllegalCallerException("Not implemented yet");
        }
    }

}
