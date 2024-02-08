package com.example.final_project_3.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class SubServiceSaveDto implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    String serviceName;
//    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String subServiceName;
    @NotNull
    private double basePrice;
    @NotBlank
    private String description;
}
