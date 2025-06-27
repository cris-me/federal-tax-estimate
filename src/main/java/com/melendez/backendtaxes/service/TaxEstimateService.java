package com.melendez.backendtaxes.service;

import com.melendez.backendtaxes.models.TaxReturn;

public interface TaxEstimateService {
    public TaxReturn findReturnByEmail(String email, int year);

    public void deleteReturnByEmail(String email, int year);

    public TaxReturn saveTaxReturn(String email, int year);
}
