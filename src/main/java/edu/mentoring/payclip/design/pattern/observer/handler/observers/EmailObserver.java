package edu.mentoring.payclip.design.pattern.observer.handler.observers;

import edu.mentoring.payclip.design.pattern.observer.domain.Observer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailObserver implements Observer {

    @Override
    public void update(String data) {
        log.info("Calling emaiiiiiiiil!");
    }

}
