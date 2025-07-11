package com.melendez.backendtaxes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Income Sources")
public class IncomeSource {

    @Id
    private String id;
    private String userId;
    private int year;
    private String employer;
    private String incomeType;
    private int income;
    private int taxWithheld;
}
