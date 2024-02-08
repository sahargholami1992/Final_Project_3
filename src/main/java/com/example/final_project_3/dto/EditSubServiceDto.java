package com.example.final_project_3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class EditSubServiceDto implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String subServiceName;
    @NotNull(message = "this filed not be null")
    private double price;
    @NotBlank(message = "this filed not be null")
    private String description;
}
