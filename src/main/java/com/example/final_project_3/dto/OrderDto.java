package com.example.final_project_3.dto;


import com.example.final_project_3.entity.enumaration.StatusOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
public class OrderDto implements Serializable {
    private Integer customerId;
    private String subServiceName;
    private String address;
    private double recommendedPrice;
    private String description;
    private LocalDate dateDoOrder;
    private StatusOrder statusOrder;

    public OrderDto(String address, double recommendedPrice, String description, LocalDate dateDoOrder) {
        this.address = address;
        this.recommendedPrice = recommendedPrice;
        this.description = description;
        this.dateDoOrder = dateDoOrder;
        this.statusOrder = StatusOrder.WAITING_FOR_EXPERT_SELECTION;
    }
}
