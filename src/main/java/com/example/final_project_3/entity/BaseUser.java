package com.example.final_project_3.entity;


import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseUser implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate dateRegister;

    @Enumerated(EnumType.STRING)
    private Roll roll;

    @Enumerated(EnumType.STRING)
    private Permissions permissions;

}
