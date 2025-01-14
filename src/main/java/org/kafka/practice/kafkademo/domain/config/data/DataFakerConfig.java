package org.kafka.practice.kafkademo.domain.config.data;

import net.datafaker.Faker;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DataFakerConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Faker dataFaker() {
        return new Faker();
    }

}
