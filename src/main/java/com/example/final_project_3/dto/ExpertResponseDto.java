package com.example.final_project_3.dto;

import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertResponseDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateRegister;
    private Roll roll;
    private Permissions permission;
    private ExpertStatus expertStatus;


}
