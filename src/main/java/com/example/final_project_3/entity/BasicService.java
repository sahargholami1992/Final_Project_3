package com.example.final_project_3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasicService implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

   @Column(unique = true)
   @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String serviceName;
    @ToString.Exclude
    @OneToMany(mappedBy = "basicService")
    private Set<SubService> subServices = new HashSet<>();


    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
