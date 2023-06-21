package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.UserAlreadyExists;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.security.AuthTokenDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class AuthenticationService {

    private final UserDao userDao;
    private final AuthTokenDao authTokenDao;

    @Autowired
    public AuthenticationService(UserDao userDao, AuthTokenDao authTokenDao) {
        this.userDao = userDao;
        this.authTokenDao = authTokenDao;
    }

    /**
     * Authenticates a user by generating a token and inserting it into the database.
     *
     * @param email The email of the user to authenticate.
     * @throws NotFoundException   if a user with the specified email is not found.
     * @throws UserAlreadyExists if the user is already logged in.
     */
    public void authenticate(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        } else if (authTokenDao.findActiveUserToken(user.getClientId()).size() == 0) {
            throw new UserAlreadyExists("User is already login");
        }

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime futureExpireTokenTime = currentTime.plusHours(8);

        log.info(String.format("A new token for user %s was set.", user.getEmail()));
        log.info(String.format("Token will expire in: %s", futureExpireTokenTime));

        authTokenDao.insertToken(email, user.getClientId(), futureExpireTokenTime);
    }
}
