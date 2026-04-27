package com.bank.banking_dev_services.transfer.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private String status;
    private String message;
    private String referenceId;
}