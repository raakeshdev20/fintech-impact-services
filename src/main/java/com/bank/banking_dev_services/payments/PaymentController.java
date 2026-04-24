package com.bank.banking_dev_services.payments;

import com.bank.banking_dev_services.payments.model.PaymentRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/submit")
    public String submitPayment(@RequestBody PaymentRequest request) {
        boolean success = paymentService.processPayment(request);
        return success ? "Payment Successful" : "Payment Failed";
    }
}
