package com.bank.banking_dev_services.transactions;

import com.bank.banking_dev_services.transactions.model.TransactionResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionsService {

    public List<TransactionResponse> getRecentTransactions() {
        return List.of(
                new TransactionResponse("TXN-99", "COMPLETED", 1500.0, "Monthly Salary"),
                new TransactionResponse("TXN-100", "PENDING", -45.0, "Grocery Store")
        );
    }
}
