package com.bank.infrastructure.impact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/dev-ops")
public class ImpactController {

    private final ImpactScanner impactScanner;

    public ImpactController(ImpactScanner impactScanner) {
        this.impactScanner = impactScanner;
    }

    @GetMapping("/test-selector")
    public Map<String, Object> getRequiredTests(@RequestParam(defaultValue = "main") String targetBranch) {
        Map<String, Object> response = new HashMap<>();
        try {
            Set<String> tags = impactScanner.getImpactedTags(targetBranch);

            response.put("status", "SUCCESS");
            response.put("impacted_tags", tags);
            response.put("target_branch", targetBranch);

            // Generate the exact command for the terminal
            String command = "mvn test -Dkarate.options=\"--tags " + String.join(",", tags) + "\"";
            response.put("suggested_command", command);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
        }
        return response;
    }
}