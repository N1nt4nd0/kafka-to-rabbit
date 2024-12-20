package org.kafka.practice.kafkademo.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KafkaRabbitApplication {

    // TODO add logging in services
    // TODO add fill random buttons to pages
    // TODO add more swagger documentation
    // TODO pin .env file to all tests

    public static void main(String[] args) {
        SpringApplication.run(KafkaRabbitApplication.class, args);
    }

}
