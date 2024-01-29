package com.example.final_project_3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer extends User {
    @OneToOne
    private Credit credit;


}
