package org.kafka.practice.kafkademo.domain.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.HobbyUseCases;
import org.kafka.practice.kafkademo.domain.config.RestControllerExceptionHandler;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class HobbyRestControllerTests {

    @InjectMocks
    private HobbyRestController sut;

    @Mock
    private HobbyUseCases hobbyUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;

    @Spy
    private RestControllerExceptionHandler exceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(exceptionHandler)
                .addPlaceholderValue("web.rest-api.endpoints.hobby-truncate", "/api/hobby/truncate")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-create", "/api/hobby/create")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-list", "/api/hobby/list")
                .addPlaceholderValue("web.rest-api.endpoints.hobby-fill", "/api/hobby/fill")
                .build();
    }

    @Test
    void testCreateNewHobbySuccessfullyWhenHobbyNameIsValid() throws Exception {
        final var expectedDtoOut = Mockito.mock(HobbyDtoOut.class);
        final var expectedHobbyName = "Hobby";

        Mockito.when(expectedDtoOut.getHobbyName()).thenReturn(expectedHobbyName);
        Mockito.when(hobbyUseCases.createHobby(Mockito.any())).thenReturn(expectedDtoOut);

        final var hobbyDtoIn = new HobbyDtoIn(expectedHobbyName);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hobbyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hobbyName").value(expectedHobbyName));
    }

    @Test
    void testSuccessfullyGetPageWithTenHobbyDtoOutElements() throws Exception {
        final var expectedPage = new PageImpl<>(IntStream.range(0, 10)
                .mapToObj(i -> Mockito.mock(HobbyDtoOut.class)).toList(), PageRequest.of(0, 10), 10);

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);
        Mockito.when(hobbyUseCases.getHobbies(Mockito.any())).thenReturn(expectedPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/hobby/list").param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));
    }

    @Test
    void testTruncateHobbiesSuccessfully() throws Exception {
        final var expectedDtoOut = Mockito.mock(TruncateTableDtoOut.class);
        final var expectedMessage = "Hobby table successfully truncated";

        Mockito.when(expectedDtoOut.getTruncateMessage()).thenReturn(expectedMessage);
        Mockito.when(hobbyUseCases.truncateHobbies()).thenReturn(expectedDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/truncate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.truncateMessage").value(expectedMessage));
    }

    @Test
    void testFillRandomTenHobbiesSuccessfully() throws Exception {
        final var expectedDtoOut = Mockito.mock(FillRandomDataDtoOut.class);
        final var expectedMessage = "Random hobbies successfully filled";
        final var expectedCount = 10;

        Mockito.when(expectedDtoOut.getMessage()).thenReturn(expectedMessage);
        Mockito.when(expectedDtoOut.getFilledCount()).thenReturn((long) expectedCount);
        Mockito.when(hobbyUseCases.fillRandomHobbies(Mockito.any())).thenReturn(expectedDtoOut);

        final var dtoIn = new FillRandomHobbiesDtoIn(expectedCount);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/fill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filledCount").value(expectedCount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void testCreateNewHobbyThrowMethodArgumentNotValidExceptionWhenHobbyNameIsEmpty() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        final var hobbyDtoIn = new HobbyDtoIn("     ");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hobbyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testGetHobbyPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/hobby/list").param("size", "50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testTruncateHobbiesThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(hobbyUseCases.truncateHobbies()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hobby/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testTruncateHobbiesThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hobby/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
