package com.melendez.backendtaxes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.melendez.backendtaxes.models.IncomeSource;
import com.melendez.backendtaxes.repositories.IncomeSourcesRepository;
import com.melendez.backendtaxes.service.IncomeSourcesService;
import com.melendez.backendtaxes.service.IncomeSourcesServiceImpl;

@SpringBootTest(classes = IncomeSourcesServiceImpl.class)
public class IncomeSourcesServiceTest {

   @Autowired
   private IncomeSourcesService service;

   @MockitoBean
   private IncomeSourcesRepository repository;

   /*
    * public boolean deleteIncomeSource(String id);
    * 
    */

   @Test
   void testGetIncomeSourcesByUserAndYear() {
      List<IncomeSource> mockList = new ArrayList<>();
      when(repository.findByUserIdAndYear(anyString(), anyInt())).thenReturn(mockList);
      List<IncomeSource> realList = service.getIncomeSourcesByUserAndYear("testEmail", 2000);
      assertEquals(mockList, realList);
   }

   @Test
   void testInsertIncomeSource() {
      IncomeSource mockIncomeSource = new IncomeSource();
      when(repository.save(any())).thenReturn(mockIncomeSource);
      IncomeSource realIncomeSource = service.insertIncomeSource(new IncomeSource());
      assertEquals(mockIncomeSource, realIncomeSource);
   }

   @ParameterizedTest
   @ValueSource(booleans = { true, false })
   void testdeleteIncomeSource(boolean value) {
      when(repository.existsById(anyString())).thenReturn(value);
      boolean result = service.deleteIncomeSource("testId");
      assertEquals(value, result);
   }
}
