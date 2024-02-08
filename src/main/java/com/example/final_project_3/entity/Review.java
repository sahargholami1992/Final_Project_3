package com.example.final_project_3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private int score;
    private String comment;
    @ManyToOne
    private Expert expert;




}
