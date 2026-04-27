package com.bank.banking_dev_services.transfer;
import com.bank.banking_dev_services.transfer.model.TransferRequest;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    public boolean processTransfer(TransferRequest request) {
        // Logic: Verify funds between accounts
        System.out.println("Processing transfer from " + request.getFromAccount() + " to " + request.getToAccount());
        return true;
    }
}
