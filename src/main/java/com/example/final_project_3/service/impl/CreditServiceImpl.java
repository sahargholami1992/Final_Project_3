package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Credit;
import com.example.final_project_3.repository.CreditRepository;
import com.example.final_project_3.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    protected final CreditRepository repository;


    @Transactional
    @Override
    public void withdraw(Credit credit) {
        repository.save(credit);
    }

    @Transactional
    @Override
    public Credit saveCredit(Credit credit) {
        return repository.save(credit);
    }


    }









