package cz.cvut.fel.nss.budgetmanager.BudgetManager.service.security;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService interface that retrieves user details from the UserDao.
 * Provides user details for authentication and authorization purposes.
 */
@Service
public class BudgetUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userDao;

    /**
     * Constructs a new BudgetUserDetailsService with the provided UserDao.
     *
     * @param userDao The UserDao implementation used to retrieve user details.
     */
    @Autowired
    public BudgetUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Loads user details by the provided email.
     *
     * @param email The email (username) of the user.
     * @return The UserDetails object containing user details.
     * @throws UsernameNotFoundException If the user with the provided email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userDao.findByEmail(email);
        if (user == null) {
            throw  new NotFoundException("User with email {"+ email +"} was not found!");
        }
        return new BudgetUserDetails(user);
    }
}
