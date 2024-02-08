package com.example.final_project_3.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class SaveExpertForSubService implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    String subServiceName;
    Integer expertId;
}
