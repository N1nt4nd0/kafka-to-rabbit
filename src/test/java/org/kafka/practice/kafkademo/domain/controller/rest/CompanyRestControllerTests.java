package org.kafka.practice.kafkademo.domain.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.config.RestControllerExceptionHandler;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
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
public class CompanyRestControllerTests {

    @InjectMocks
    private CompanyRestController sut;

    @Mock
    private CompanyUseCases companyUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;

    @Spy
    private RestControllerExceptionHandler exceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(exceptionHandler)
                .addPlaceholderValue("web.rest-api.endpoints.company-truncate", "/api/company/truncate")
                .addPlaceholderValue("web.rest-api.endpoints.company-create", "/api/company/create")
                .addPlaceholderValue("web.rest-api.endpoints.company-list", "/api/company/list")
                .addPlaceholderValue("web.rest-api.endpoints.company-fill", "/api/company/fill")
                .build();
    }

    @Test
    void testCreateNewCompanySuccessfullyWhenCompanyNameIsValid() throws Exception {
        final var expectedDtoOut = Mockito.mock(CompanyDtoOut.class);
        final var expectedCompanyName = "Company";

        Mockito.when(expectedDtoOut.getCompanyName()).thenReturn(expectedCompanyName);
        Mockito.when(companyUseCases.createCompany(Mockito.any())).thenReturn(expectedDtoOut);

        final var companyDtoIn = new CompanyDtoIn(expectedCompanyName);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(expectedCompanyName));
    }

    @Test
    void testSuccessfullyGetPageWithTenCompanyDtoOutElements() throws Exception {
        final var expectedPage = new PageImpl<>(IntStream.range(0, 10)
                .mapToObj(i -> Mockito.mock(CompanyDtoOut.class)).toList(), PageRequest.of(0, 10), 10);

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);
        Mockito.when(companyUseCases.getCompanies(Mockito.any())).thenReturn(expectedPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/list").param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));
    }

    @Test
    void testTruncateCompaniesSuccessfully() throws Exception {
        final var expectedDtoOut = Mockito.mock(TruncateTableDtoOut.class);
        final var expectedMessage = "Company table successfully truncated";

        Mockito.when(expectedDtoOut.getTruncateMessage()).thenReturn(expectedMessage);
        Mockito.when(companyUseCases.truncateCompanies()).thenReturn(expectedDtoOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/truncate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.truncateMessage").value(expectedMessage));
    }

    @Test
    void testFillRandomTenCompaniesSuccessfully() throws Exception {
        final var expectedDtoOut = Mockito.mock(FillRandomDataDtoOut.class);
        final var expectedMessage = "Random companies successfully filled";
        final var expectedCount = 10;

        Mockito.when(expectedDtoOut.getMessage()).thenReturn(expectedMessage);
        Mockito.when(expectedDtoOut.getFilledCount()).thenReturn((long) expectedCount);
        Mockito.when(companyUseCases.fillRandomCompanies(Mockito.any())).thenReturn(expectedDtoOut);

        final var dtoIn = new FillRandomCompaniesDtoIn(expectedCount);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/fill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filledCount").value(expectedCount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void testCreateNewCompanyThrowMethodArgumentNotValidExceptionWhenCompanyNameIsEmpty() throws Exception {
        final var expectedMessagePrefix = "Validation failed";

        final var companyDtoIn = new CompanyDtoIn("     ");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage")
                        .value(Matchers.startsWith(expectedMessagePrefix)));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testGetCompanyPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/list").param("size", "50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testTruncateCompaniesThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(companyUseCases.truncateCompanies()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));

        Mockito.verify(exceptionHandler).handleException(Mockito.any());
    }

    @Test
    void testTruncateCompaniesThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
