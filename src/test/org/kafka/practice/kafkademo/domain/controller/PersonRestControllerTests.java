package org.kafka.practice.kafkademo.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.config.RestControllerExceptionHandler;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PersonRestControllerTests {

    @InjectMocks
    private PersonRestController sut;

    @Mock
    private PersonUseCases personUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new RestControllerExceptionHandler())
                .addPlaceholderValue("web.rest-api.endpoints.person-list", "/api/person/list")
                .addPlaceholderValue("web.rest-api.endpoints.person-fill", "/api/person/fill")
                .addPlaceholderValue("web.rest-api.endpoints.person-truncate", "/api/person/truncate")
                .build();
    }

    @Test
    void testGetPersonPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/list?size=50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncatePersonsThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(personUseCases.truncatePersons()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncatePersonsThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
