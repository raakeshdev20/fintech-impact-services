package com.bank.banking_dev_services.payments;

import com.bank.banking_dev_services.payments.model.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean processPayment(PaymentRequest request) {
        if (request.getAmount() <= 0) {
            System.out.println("Payment Rejected: Invalid Amount");
            return false;
        }
        //test
        System.out.println("Test12");
        System.out.println("Processing payment for: " + request.getVendorName());
        return true;
    }
}