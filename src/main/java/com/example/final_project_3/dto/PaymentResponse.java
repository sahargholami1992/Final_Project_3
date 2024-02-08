package com.example.final_project_3.dto;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    private boolean success;

    public PaymentResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
