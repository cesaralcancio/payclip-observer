package edu.mentoring.payclip.design.pattern.observer.configuration;

import edu.mentoring.payclip.design.pattern.observer.domain.Observer;
import edu.mentoring.payclip.design.pattern.observer.domain.TopicName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ObserverManagerConfiguration {

    @Bean
    public Map<TopicName, List<Observer>> observers() {
        Map<TopicName, List<Observer>> observers = new HashMap<>();

        Arrays.stream(TopicName.values())
                .forEach(observer -> observers.put(observer, new ArrayList<>()));

        return observers;
    }

}
