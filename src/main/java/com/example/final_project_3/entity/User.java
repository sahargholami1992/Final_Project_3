package com.example.final_project_3.entity;


import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String lastName;
    @Column(unique = true)
    @NotBlank
    @Email
    private String email;
    @Column( nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8}$")
    private String password;
    private LocalDate dateRegister;
    @Enumerated(EnumType.STRING)
    private Roll roll;
    @Enumerated(EnumType.STRING)
    private Permissions permissions;

    @Override
    public String toString() {
        return " id = " +getId()+
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateRegister=" + dateRegister +
                ", roll=" + roll +
                ", permission=" + permissions;
    }
}
