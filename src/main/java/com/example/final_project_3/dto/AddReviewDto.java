package com.example.final_project_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AddReviewDto implements Serializable {
    private Integer offerId;
    private int score;
    private String comment;

}
