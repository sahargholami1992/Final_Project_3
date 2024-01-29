package com.example.final_project_3.service.dto;


import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class CustomerRegisterDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateRegister;
    private Roll roll;
    private Permissions permission;


    public CustomerRegisterDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateRegister = LocalDate.now();
        this.roll = Roll.CUSTOMER;
        this.permission = Permissions.ACCEPTED;
    }
}
