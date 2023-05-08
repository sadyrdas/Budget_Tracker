package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {
    private final UserDao userDao;


    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Boolean createUser(String email, String username, String password){
        Objects.requireNonNull(email);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        Boolean result = false;
        if (email.isEmpty() ||  username.isEmpty() || password.isEmpty()){
            return  result;
        }else {
            User user  = new User(email, username, password);
            userDao.persist(user);
            result = true;

        }
        return result;

    }
    @Transactional
    public User findUserByEmail(String userEmail) {
        return userDao.findByEmail(userEmail);
    }
    @Transactional
    public Boolean deleteUserByEmail(String email) {
        Objects.requireNonNull(email);

        Boolean result = false;
        if(email.isEmpty()) {
            return result;
        }else if (userDao.findByEmail(email) == null){
            return result;
        }else {
            userDao.deleteUserByEmail(email);
        }
        return true;
    }



}
