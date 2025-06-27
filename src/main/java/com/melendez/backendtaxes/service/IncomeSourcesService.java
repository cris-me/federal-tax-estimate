package com.melendez.backendtaxes.service;

import java.util.List;

import com.melendez.backendtaxes.models.IncomeSource;

public interface IncomeSourcesService {
    public List<IncomeSource> getIncomeSourcesByUserAndYear(String userId, int year);

    public IncomeSource insertIncomeSource(IncomeSource incomeSource);

    public boolean deleteIncomeSource(String id);
}
