package com.bank.banking_dev_services.transfer;

import com.bank.banking_dev_services.transfer.model.TransferRequest;
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
    public String execute(@RequestBody TransferRequest request) {
        boolean success = transferService.processTransfer(request);
        return success ? "Transfer Successful" : "Transfer Failed";
    }
}
