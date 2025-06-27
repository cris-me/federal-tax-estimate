package com.melendez.backendtaxes.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.melendez.backendtaxes.models.IncomeSource;
import com.melendez.backendtaxes.service.IncomeSourcesService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IncomeSourcesController.class)
@AutoConfigureMockMvc
public class IncomeSourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IncomeSourcesService incomeSourcesService;

    @Test
    void testGetIncomeSourcesByUserAndYear_200() throws Exception {
        IncomeSource mockSource = new IncomeSource();
        List<IncomeSource> mockList = new ArrayList<>();
        mockList.add(mockSource);
        when(incomeSourcesService.getIncomeSourcesByUserAndYear(anyString(), anyInt())).thenReturn(mockList);
        String userId = "1234";
        mockMvc.perform(get("/api/income/userandyear")
                .header("userId", userId).header("year", 2000))
                .andExpect(status().isOk());
        verify(incomeSourcesService, times(1)).getIncomeSourcesByUserAndYear(userId, 2000);
    }

    @Test
    void testGetIncomeSourcesByUserAndYear_204() throws Exception {
        List<IncomeSource> mockList = new ArrayList<>();
        when(incomeSourcesService.getIncomeSourcesByUserAndYear(anyString(), anyInt())).thenReturn(mockList);
        String userId = "1234";
        mockMvc.perform(get("/api/income/userandyear")
                .header("userId", userId)
                .header("year", 2000))
                .andExpect(status().isNoContent());
        verify(incomeSourcesService, times(1)).getIncomeSourcesByUserAndYear(userId, 2000);
    }
}
