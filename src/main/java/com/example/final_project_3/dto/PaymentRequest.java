package com.example.final_project_3.dto;


import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class PaymentRequest implements Serializable {
    @Pattern(regexp ="^[0-9]{16}$")
    private String cardNumber;
    @Min(3)
    @Max(6)
    private int cvv;
    private String month;
    private String day;

    private String password;
//    @Transient
//    private String captcha;
//
//    @Transient
//    private String hiddenCaptcha;
//
//    @Transient
//    private String realCaptcha;
}
