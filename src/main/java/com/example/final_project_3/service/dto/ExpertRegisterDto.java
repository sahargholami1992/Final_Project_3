package com.example.final_project_3.service.dto;

import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.security.Permission;
import java.time.LocalDate;

@Getter
@Setter
public class ExpertRegisterDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateRegister;
    private Roll roll;
    private Permissions permission;
    private ExpertStatus expertStatus;
    private int score;

    private byte[] profileImage;

    public ExpertRegisterDto(String firstName, String lastName, String email, String password, byte[] profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateRegister = LocalDate.now();
        this.roll = Roll.EXPERT;
        this.permission = Permissions.WAITING;
        this.expertStatus = ExpertStatus.AWAITING_CONFIRMATION;
        this.score = 0;
        this.profileImage = profileImage;
    }
}

