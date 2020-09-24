package edu.mentoring.payclip.design.pattern.observer.configuration.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SlackProperties {

    @Value("${service.slack.hook}")
    private String hookUrl;

}
