package com.bank.banking_dev_services.transactions.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String id;
    private String status;
    private double amount;
    private String description;
}
