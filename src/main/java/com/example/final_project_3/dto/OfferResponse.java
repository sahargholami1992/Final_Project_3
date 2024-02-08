package com.example.final_project_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class OfferResponse implements Serializable {
    private int DurationOfWork;
    private LocalDate SuggestedTimeToStartWork;
    private double recommendedPrice;
    private LocalDate dateRegisterOffer;
}
