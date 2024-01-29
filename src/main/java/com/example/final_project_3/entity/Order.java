package com.example.final_project_3.entity;


import com.example.final_project_3.entity.enumaration.StatusOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private SubService subService;
    @NotBlank
    private String address;
    @NotNull
    private double recommendedPrice;
    private String description;
    private LocalDate dateDoOrder;
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    @OneToMany(mappedBy = "order")
    private List<Offer> offers;
    @OneToOne
    private Comment comment;

}
