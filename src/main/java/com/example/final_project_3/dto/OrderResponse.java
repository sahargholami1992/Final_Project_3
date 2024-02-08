package com.example.final_project_3.dto;

import com.example.final_project_3.entity.enumaration.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private Integer orderId;
    private String address;
    private double recommendedPrice;
    private String description;
    private LocalDate dateDoOrder;
    private StatusOrder statusOrder;
}
