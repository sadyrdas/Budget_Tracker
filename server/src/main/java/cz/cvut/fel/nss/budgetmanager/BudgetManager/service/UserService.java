package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.security.AuthTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Service class for managing user-related operations.
 */
@Service
@Transactional
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final AuthTokenDao authTokenRepository;

    /**
     * Constructs a new UserService with the given dependencies.
     *
     * @param userDao         The UserDao to interact with the user data.
     * @param passwordEncoder The PasswordEncoder for encoding user passwords.
     * @param walletService   The WalletService for managing user wallets.
     */
    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, WalletService walletService, AuthTokenDao authTokenRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
        this.authTokenRepository = authTokenRepository;
    }

    /**
     * Creates a new user with the given email, username, and password.
     *
     * @param email    The email of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the user is created successfully, false otherwise.
     */
    public Boolean createUser(String email, String username, String password) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        User exist = userDao.findByEmail(email);

        boolean result = false;
        if (exist != null && (email.isEmpty() || username.isEmpty() || password.isEmpty())) {
            return result;
        } else {
            User user = new User(email, password, username, null);
            user.encodePassword(passwordEncoder);
            Wallet wallet = walletService.createWallet(username, user);
            user.setWallet(wallet);

            userDao.persist(user);
            result = true;
        }
        return result;
    }

    public User findUser(Long id) {
        return userDao.find(id);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param userEmail The email of the user.
     * @return The User object if found, null otherwise.
     */
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
