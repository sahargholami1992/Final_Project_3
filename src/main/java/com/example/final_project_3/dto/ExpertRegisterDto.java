package com.example.final_project_3.dto;

import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpertRegisterDto extends CustomerRegisterDto {

    private ExpertStatus expertStatus;
    private int score;
    private String imagePath;

    public ExpertRegisterDto(String firstName, String lastName, String email, String password, String imagePath) {
        super(firstName,lastName,email,password);
        super.setRoll(Roll.EXPERT) ;
        super.setPermission(Permissions.WAITING);
        this.expertStatus = ExpertStatus.AWAITING_CONFIRMATION;
        this.score = 0;
        this.imagePath = imagePath;
    }

}

