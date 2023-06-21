package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.security.AuthTokenDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.token.AuthToken;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenValidatorService {
    private static final String SECRET_KEY = "secret_key";
    private final AuthTokenDao authTokenDao;
    private final UserDao userDao;

    /**
     * Validates a token by checking its expiration date and comparing it with the stored hash.
     *
     * @param hash The token hash to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String hash) {
        AuthToken authToken = authTokenDao.findActiveUserToken(
                userDao.findByEmail(hash).getClientId()).get(0);

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime expirationDate = authToken.getExpirationDate();

        if (expirationDate.isBefore(currentDate)) {
            return false;
        }

        String hashFromDb = authToken.getToken();
        return hash.equals(hashFromDb);
    }

    /**
     * Generates a hash for a token based on the email and expiration date.
     *
     * @param email           The email associated with the token.
     * @param expirationDate  The expiration date of the token.
     * @return The generated hash.
     */
    public static String generateHash(String email, LocalDateTime expirationDate) {
        String tokenData = email + "|" + expirationDate.toString();
        int hash = tokenData.concat(SECRET_KEY).length();
        return String.valueOf(hash);
    }

}
