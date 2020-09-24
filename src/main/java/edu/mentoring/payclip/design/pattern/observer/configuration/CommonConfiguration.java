package edu.mentoring.payclip.design.pattern.observer.configuration;

import com.google.gson.Gson;
import com.payclip.common.util.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    public Gson gson() {
        return GsonFactory.create();
    }

}
