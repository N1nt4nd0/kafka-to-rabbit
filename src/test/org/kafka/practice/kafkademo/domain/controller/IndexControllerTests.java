package org.kafka.practice.kafkademo.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.config.ControllerExceptionHandler;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class IndexControllerTests {

    @InjectMocks
    private IndexController sut;

    @Spy
    private ControllerExceptionHandler exceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(exceptionHandler)
                .build();
    }

    @Test
    void testReturnsCorrectIndexView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    void testReturnsCorrectErrorViewWhenTestRuntimeExceptionOccurred() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").flashAttr("do-test-runtime-exception", true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"));

        Mockito.verify(exceptionHandler).handleException(Mockito.any(Exception.class), Mockito.any());
    }

}
