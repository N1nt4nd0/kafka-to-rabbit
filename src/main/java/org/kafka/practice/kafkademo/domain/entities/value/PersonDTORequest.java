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
public class PersonDTORequest {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;

}
