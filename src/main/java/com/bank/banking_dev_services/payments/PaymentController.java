package com.bank.banking_dev_services.payments;

import com.bank.banking_dev_services.payments.model.PaymentRequest;
import com.bank.banking_dev_services.payments.model.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/submit")
    public ResponseEntity<PaymentResponse> submitPayment(@RequestBody PaymentRequest request) {
        // Logic to process
        boolean success = paymentService.processPayment(request);

        PaymentResponse response = new PaymentResponse(
                success ? "SUCCESS" : "FAILED",
                success ? "Payment Successful" : "Payment Failed",
                "TXN-" + System.currentTimeMillis() // Generates a fake ID for testing
        );

        return ResponseEntity.ok(response);
    }
}
