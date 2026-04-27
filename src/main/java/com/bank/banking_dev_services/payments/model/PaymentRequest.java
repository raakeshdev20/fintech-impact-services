package com.bank.banking_dev_services.payments.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    // Getters and Setters
    private String vendorName;
    private double amount;
    private String currency;

}
