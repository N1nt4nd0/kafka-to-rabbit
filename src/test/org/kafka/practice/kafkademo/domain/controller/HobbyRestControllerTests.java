package org.kafka.practice.kafkademo.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.HobbyUseCases;
import org.kafka.practice.kafkademo.domain.config.RestControllerExceptionHandler;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class HobbyRestControllerTests {

    @InjectMocks
    private HobbyRestController sut;

    @Mock
    private HobbyUseCases hobbyUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new RestControllerExceptionHandler())
                .addPlaceholderValue("web.rest-api.endpoints.hobby-truncate", "/api/hobby/truncate")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-create", "/api/hobby/create")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-list", "/api/hobby/list")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-fill", "/api/hobby/fill")
                .build();
    }

    @Test
    void testCreateNewHobbyThrowMethodArgumentNotValidExceptionWhenHobbyNameIsEmpty() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new HobbyDtoIn("     "))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));
    }

    @Test
    void testCreateNewHobbySuccessfullyWhenHobbyNameIsValid() throws Exception {
        final var expectedHobbyName = "Hobby";
        final var validHobbyDtoOut = Mockito.mock(HobbyDtoOut.class);

        Mockito.when(validHobbyDtoOut.getHobbyName()).thenReturn(expectedHobbyName);
        Mockito.when(hobbyUseCases.createHobby(Mockito.any(HobbyDtoIn.class))).thenReturn(validHobbyDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new HobbyDtoIn(expectedHobbyName))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hobbyName").value(expectedHobbyName));
    }

    @Test
    void testGetHobbyPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/hobby/list?size=50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncateHobbiesThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(hobbyUseCases.truncateHobbies()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncateHobbiesThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hobby/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
