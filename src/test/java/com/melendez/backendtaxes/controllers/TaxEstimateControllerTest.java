package com.melendez.backendtaxes.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.melendez.backendtaxes.config.SecurityConfig;
import com.melendez.backendtaxes.models.TaxReturn;
import com.melendez.backendtaxes.service.TaxEstimateService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@WebMvcTest(TaxEstimateController.class)
@Import(SecurityConfig.class)
public class TaxEstimateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TaxEstimateService taxEstimateService;

    @Test
    void testGetOneReturn() throws Exception {
        TaxReturn taxReturn = new TaxReturn();
        when(taxEstimateService.findReturnByEmail(anyString(), anyInt())).thenReturn(taxReturn);
        String email = "test@gmail.com";
        mockMvc.perform(get("/api/taxes")
                .param("email", email)
                .param("year", "2000"))
                .andExpect(status().isOk());
        verify(taxEstimateService, times(1)).findReturnByEmail(email, 2000);
    }

    @Test
    void testCreateReturn() throws Exception {
        TaxReturn taxReturn = new TaxReturn();
        when(taxEstimateService.saveTaxReturn(anyString(), anyInt())).thenReturn(taxReturn);
        String email = "test@gmail.com";
        mockMvc.perform(post("/api/taxes/new")
                .param("email", email)
                .param("year", "2000")
                .with(user("testUser").roles("USER")))
                .andExpect(status().isOk());
        verify(taxEstimateService, times(1)).saveTaxReturn(email, 2000);
    }

    @Test
    void testCreateReturn_403() throws Exception {
        TaxReturn taxReturn = new TaxReturn();
        when(taxEstimateService.saveTaxReturn(anyString(), anyInt())).thenReturn(taxReturn);
        String email = "test@gmail.com";
        mockMvc.perform(post("/api/taxes/new")
                .param("email", email)
                .param("year", "2000"))
                .andExpect(status().isForbidden());
    }
}
