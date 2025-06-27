package com.melendez.backendtaxes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melendez.backendtaxes.models.IncomeSource;
import com.melendez.backendtaxes.service.IncomeSourcesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/income")
public class IncomeSourcesController {

    private final IncomeSourcesService incomeSourcesService;

    @GetMapping("/userandyear")
    public ResponseEntity<List<IncomeSource>> getIncomeSourcesByUserAndYear(@RequestHeader String userId,
            @RequestHeader int year) {
        List<IncomeSource> incomeSources = incomeSourcesService.getIncomeSourcesByUserAndYear(userId, year);
        if (incomeSources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(incomeSources);
    }
}
