package edu.mentoring.payclip.design.pattern.observer.handler.observers;

import com.payclip.financial.service.common.util.slack.SlackNotification;
import com.payclip.financial.service.common.util.slack.template.SlackErrorTemplate;
import edu.mentoring.payclip.design.pattern.observer.domain.Observer;
import edu.mentoring.payclip.design.pattern.observer.service.SlackService;
import org.springframework.stereotype.Component;

@Component
public class SlackObserver implements Observer {

    private final SlackService slackService;

    public SlackObserver(SlackService slackService) {
        this.slackService = slackService;
    }

    @Override
    public void update(String data) {
        SlackNotification errorNotification = SlackErrorTemplate
                .basicTemplate("CESAAAAAAR ALCANCIOOOOOO", null, data);
        slackService.sendNotification(errorNotification);
    }
}
