package org.kafka.practice.kafkademo.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO пароли, важные переменные прописывать в .env файле и подключить его к запуску
// TODO подключить LiquidBase
// TODO делать DtoRequest и DtoResponse а не гонять один Dto в разные стороны

@SpringBootApplication
@EnableTransactionManagement
public class KafkaRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaRabbitApplication.class, args);
    }

}
