package com.bank.banking_dev_services.transfer;

import com.bank.banking_dev_services.transfer.model.TransferRequest;
import com.bank.banking_dev_services.transfer.model.TransferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    // Constructor Injection
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/execute")
    public ResponseEntity<TransferResponse> execute(@RequestBody TransferRequest request) {
        // 1. Process the transfer
        boolean success = transferService.processTransfer(request);

        // 2. Build the structured response object
        TransferResponse response = new TransferResponse();
        response.setStatus(success ? "SUCCESS" : "FAILED");
        response.setMessage(success ? "Transfer Successful" : "Transfer Failed");
        response.setReferenceId("REF-" + System.currentTimeMillis());

        // 3. Return as a proper ResponseEntity (JSON)
        return ResponseEntity.ok(response);
    }
    }
