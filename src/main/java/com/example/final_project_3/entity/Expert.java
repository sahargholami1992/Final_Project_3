package com.example.final_project_3.entity;


import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.utill.ValidImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expert extends User {
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;
    private int score;
    @Lob
    @ValidImage
    private byte[] profileImage;
    @OneToOne
    private Credit credit;
    @ToString.Exclude
    @ManyToMany(mappedBy = "experts")
    private Set<SubService> subServices = new HashSet<>();

    @Override
    public String toString() {
        return "Expert{" +
                super.toString()+
                ", expertStatus=" + expertStatus +
                ", score=" + score +
                "} " ;
    }
}
