package com.example.final_project_3.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class AddBasicServiceDto implements Serializable {
    @Column(unique = true)
   @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String serviceName;
}
