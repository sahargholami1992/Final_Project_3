package com.example.final_project_3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Offer implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private int DurationOfWork;
    private LocalDate SuggestedTimeToStartWork;
    @NotNull
    private double recommendedPrice;
    private LocalDate dateRegisterOffer;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Expert expert;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Order order;

}
