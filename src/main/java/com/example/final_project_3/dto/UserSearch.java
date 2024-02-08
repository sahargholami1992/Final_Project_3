package com.example.final_project_3.dto;

import com.example.final_project_3.entity.enumaration.Roll;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearch {
    private Roll role;
    private String firstName;
    private String lastName;
    private String email;
    private String expertiseField;
    private int minRating;
    private int maxRating;
}
