package com.bank.banking_dev_services.transactions;
import com.bank.banking_dev_services.transactions.model.TransactionResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsService transactionService;

    public TransactionsController(TransactionsService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/history")
    public List<TransactionResponse> getHistory() {
        return transactionService.getRecentTransactions();
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable String id) {
        // Simulating a lookup
        return new TransactionResponse(id, "COMPLETED", 100.0, "Details for " + id);
    }
}
