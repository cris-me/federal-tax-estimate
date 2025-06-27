package com.melendez.backendtaxes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.melendez.backendtaxes.models.IncomeSource;
import com.melendez.backendtaxes.repositories.IncomeSourcesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeSourcesServiceImpl implements IncomeSourcesService {
    
    private final IncomeSourcesRepository incomeSourcesRepository;

    @Override
    public List<IncomeSource> getIncomeSourcesByUserAndYear(String userId, int year) {
        log.info("Getting source documents for one user for the year {}", year);
        return incomeSourcesRepository.findByUserIdAndYear(userId, year);
    }

    @Override
    public IncomeSource insertIncomeSource(IncomeSource incomeSource) {
        IncomeSource saved = incomeSourcesRepository.save(incomeSource);
        return saved;
    }

    //return true if deletion successful
    @Override
    public boolean deleteIncomeSource(String id) {
        if(!incomeSourcesRepository.existsById(id)){
            return false;
        }
        incomeSourcesRepository.deleteById(id);
        return true;
    }

}
