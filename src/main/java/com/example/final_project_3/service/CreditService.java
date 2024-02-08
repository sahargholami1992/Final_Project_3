package com.example.final_project_3.service;


import com.example.final_project_3.entity.Credit;

public interface CreditService {

    void withdraw(Credit credit);

    Credit saveCredit(Credit credit);
}
