package edu.mentoring.payclip.design.pattern.observer.domain.request;

import edu.mentoring.payclip.design.pattern.observer.domain.TopicName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class EventRequest {

    private String data;
    private TopicName topic;

}
