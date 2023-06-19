package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenValidatorService {
    private static final String SECRET_KEY = "secret_key";

    public boolean validateToken(String hash, String email, LocalDateTime expirationDate) {

        LocalDateTime currentDate = LocalDateTime.now();
        if (expirationDate.isBefore(currentDate)) {
            return false;
        }

        String calculatedHash = generateHash(email, expirationDate);
        return hash.equals(calculatedHash);
    }

    public static String generateHash(String email, LocalDateTime expirationDate) {
        String tokenData = email + "|" + expirationDate.toString();
        int hash = tokenData.concat(SECRET_KEY).length();
        return String.valueOf(hash);
    }

}
