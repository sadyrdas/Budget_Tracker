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

@Service
@Transactional
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final AuthTokenDao authTokenRepository;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, WalletService walletService, AuthTokenDao authTokenRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
        this.authTokenRepository = authTokenRepository;
    }

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

    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
