package com.bank.banking_dev_services.transactions;
import com.bank.banking_dev_services.transactions.model.TransactionResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TransactionResponse>> getHistory() {
        List<TransactionResponse> history = transactionService.getRecentTransactions();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String id) {
        // In a real app, you'd check if the transaction exists
        TransactionResponse response = new TransactionResponse(id, "COMPLETED", 100.0, "Details for " + id);
        return ResponseEntity.ok(response);
    }
}
