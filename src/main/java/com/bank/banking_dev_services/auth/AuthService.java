package com.bank.banking_dev_services.auth;

import org.springframework.stereotype.Service;
import java.util.UUID;

    @Service
    public class AuthService {

        /**
         * Simulates a critical security check.
         * In our Impact Analysis model, changing this logic
         * triggers a full @regression suite.
         */
        public boolean validateSession(String token) {
            // Simple mock logic: if the token is not null and long enough, it's "valid"
            if (token == null || token.length() < 10) {
                return false;
            }

            System.out.println("Security: Session validated for token prefix: " + token.substring(0, 5));
            return true;
        }

        public String generateMfaToken() {
            return "MFA-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        }
    }

