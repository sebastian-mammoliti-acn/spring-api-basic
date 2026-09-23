package com.example.demo;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "message", "Spring Boot API is running",
            "protectedEndpoint", "/hello"
        );
    }

    @GetMapping("/hello")
    public Map<String, Object> hello(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
            "message", "JWT authentication successful",
            "subject", jwt.getSubject(),
            "issuer", jwt.getIssuer().toString(),
            "clientId", getClientId(jwt)
        );
    }

    private String getClientId(Jwt jwt) {
        String authorizedParty = jwt.getClaimAsString("azp");

        if (authorizedParty != null) {
            return authorizedParty;
        }

        return "unknown";
    }
}