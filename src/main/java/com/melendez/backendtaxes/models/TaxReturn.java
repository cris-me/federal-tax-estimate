package com.melendez.backendtaxes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Tax Returns")
public class TaxReturn {

    @Id
    private String id;
    private String userId;
    private String filingStatus;
    private int year;
    private double totalIncome;
    private double totalWithheld;
    private double taxableIncome;
    private double estimatedTaxOwed;
    private double totalCredits;
    private int refundOrOwed;
}