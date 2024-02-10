package com.example.final_project_3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class BasicService implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String serviceName;
    @ToString.Exclude
    @OneToMany(mappedBy = "basicService")
    private Set<SubService> subServices = new HashSet<>();
    @JsonIgnore
    public Set<SubService> getSubServices(){
        return subServices;
    }


}
