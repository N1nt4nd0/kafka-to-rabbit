package org.kafka.practice.kafkademo.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
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
public class CompanyRestControllerTests {

    @InjectMocks
    private CompanyRestController sut;

    @Mock
    private CompanyUseCases companyUseCases;

    @Mock
    private WebPagesConfig webPagesConfig;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new RestControllerExceptionHandler())
                .addPlaceholderValue("web.rest-api.endpoints.company-list", "/api/company/list")
                .addPlaceholderValue("web.rest-api.endpoints.company-fill", "/api/company/fill")
                .addPlaceholderValue("web.rest-api.endpoints.company-create", "/api/company/create")
                .addPlaceholderValue("web.rest-api.endpoints.company-truncate", "/api/company/truncate")
                .build();
    }

    @Test
    void testGetCompanyPageThrowPageSizeLimitExceptionWhenMaxPageSizeEqualsTen() throws Exception {
        final var expectedMessage = "Page size must be less than 10";

        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/list?size=50"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncateCompaniesThrowUnexpectedRuntimeException() throws Exception {
        final var expectedMessage = "Unexpected error occurred";

        Mockito.when(companyUseCases.truncateCompanies()).thenThrow(new RuntimeException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company/truncate"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(expectedMessage));
    }

    @Test
    void testTruncateCompaniesThrowHttpRequestMethodNotSupportedExceptionWhenMethodIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/truncate"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

}
