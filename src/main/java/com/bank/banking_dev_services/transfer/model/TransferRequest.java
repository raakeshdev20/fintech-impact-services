package com.bank.banking_dev_services.transfer.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private double amount;
}
