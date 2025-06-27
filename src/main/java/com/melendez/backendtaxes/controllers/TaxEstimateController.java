package com.melendez.backendtaxes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melendez.backendtaxes.models.TaxReturn;
import com.melendez.backendtaxes.service.TaxEstimateService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taxes")
public class TaxEstimateController {

    private final TaxEstimateService taxEstimateService;

    @GetMapping()
    public ResponseEntity<TaxReturn> getOneReturn(@RequestParam String email, @RequestParam int year) {
        TaxReturn taxReturn = taxEstimateService.findReturnByEmail(email, year);
        return ResponseEntity.ok(taxReturn);
    }

    @PostMapping("/new")
    public ResponseEntity<TaxReturn> createReturn(@RequestParam String email, @RequestParam int year) {
        TaxReturn taxReturn = taxEstimateService.saveTaxReturn(email, year);
        return ResponseEntity.ok(taxReturn);

    }

}
