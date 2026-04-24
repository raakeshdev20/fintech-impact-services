package com.bank.banking_dev_services.transactions.model;

public class TransactionResponse {
    public String id;
    public String status;
    public double amount;
    public String description;

    public TransactionResponse(String id, String status, double amount, String description) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.description = description;
    }
}
