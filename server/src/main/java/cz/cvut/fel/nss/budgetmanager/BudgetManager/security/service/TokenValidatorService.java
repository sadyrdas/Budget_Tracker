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

    public static String generateHash(String email, LocalDateTime expirationDate) {
        String tokenData = email + "|" + expirationDate.toString();
        int hash = tokenData.concat(SECRET_KEY).length();
        return String.valueOf(hash);
    }

}
