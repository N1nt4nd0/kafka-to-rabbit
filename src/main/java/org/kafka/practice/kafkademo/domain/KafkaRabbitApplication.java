package org.kafka.practice.kafkademo.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KafkaRabbitApplication {

    // TODO make DtoFields final
    // TODO remove @Setter data from entities, make setters protected
    // TODO liquidBase changelog in sql files
    // TODO rabbit make one exchange, two queues and two routing keys
    // TODO make json rest controller request for updating persons data

    public static void main(String[] args) {
        SpringApplication.run(KafkaRabbitApplication.class, args);
    }

}
