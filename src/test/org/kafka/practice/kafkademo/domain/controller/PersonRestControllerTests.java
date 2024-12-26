package org.kafka.practice.kafkademo.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.config.RestControllerExceptionHandler;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoOut;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class PersonRestControllerTests {

    @InjectMocks
    private PersonRestController sut;

    @Mock
    private PersonUseCases personUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;

    @Spy
    private RestControllerExceptionHandler exceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(exceptionHandler)
                .addPlaceholderValue("web.rest-api.endpoints.person-truncate", "/api/person/truncate")
                .addPlaceholderValue("web.rest-api.endpoints.person-create", "/api/person/create")
                .addPlaceholderValue("web.rest-api.endpoints.person-list", "/api/person/list")
                .addPlaceholderValue("web.rest-api.endpoints.person-fill", "/api/person/fill")
                .build();
    }

    @Test
    void testCreateNewPersonThrowMethodArgumentNotValidExceptionWhenEmailIsInvalid() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(new PersonDtoIn("invalid_email", "FirstName", "LastName"))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class));
    }

    @Test
    void testCreateNewPersonThrowMethodArgumentNotValidExceptionWhenFirstNameIsEmpty() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(new PersonDtoIn("test@email.com", "     ", "LastName"))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class));
    }

    @Test
    void testCreateNewPersonThrowMethodArgumentNotValidExceptionWhenLastNameIsEmpty() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(new PersonDtoIn("test@email.com", "FirstName", "     "))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class));
    }

    @Test
    void testCreateNewPersonSuccessfullyWhenAllDataIsValid() throws Exception {
        final var expectedEmail = "test@test.com";
        final var validPersonDtoOut = Mockito.mock(PersonDtoOut.class);

        Mockito.when(validPersonDtoOut.getEmail()).thenReturn(expectedEmail);
        Mockito.when(personUseCases.createPerson(Mockito.any(PersonDtoIn.class))).thenReturn(validPersonDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(new PersonDtoIn(expectedEmail, "FirstName", "LastName"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedEmail));
    }

    @Test
    void testGetPersonPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/list").param("size", "50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class));
    }

    @Test
    void testSuccessfullyGetPageWithTenPersonDtoOutElements() throws Exception {
        final var expectedPage = new PageImpl<>(IntStream.range(0, 10)
                .mapToObj(i -> Mockito.mock(PersonDtoOut.class)).toList(), PageRequest.of(0, 10), 10);

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);
        Mockito.when(personUseCases.getPersons(Mockito.any(Pageable.class))).thenReturn(expectedPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/list").param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));
    }

    @Test
    void testTruncatePersonsThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(personUseCases.truncatePersons()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class));
    }

    @Test
    void testTruncatePersonsThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
