package edu.mentoring.payclip.design.pattern.observer.service;

import com.payclip.financial.service.common.util.slack.SlackNotification;
import edu.mentoring.payclip.design.pattern.observer.configuration.properties.SlackProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class SlackService {

    private final SlackProperties slackProperties;

    public SlackService(SlackProperties slackProperties) {
        this.slackProperties = slackProperties;
    }

    @Async
    public void sendNotification(SlackNotification notification) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(notification.toJson(), headers);

        RestTemplate template = new RestTemplate();
        template.postForLocation(slackProperties.getHookUrl(), entity);
    }
}
