package org.kafka.practice.kafkademo.domain.entities.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private UUID id;
    private String firstName;
    private String lastName;

}
