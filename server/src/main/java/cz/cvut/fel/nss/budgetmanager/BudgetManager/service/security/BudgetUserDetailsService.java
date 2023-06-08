//package cz.cvut.fel.nss.budgetmanager.BudgetManager.service.security;
//
//import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
//import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
//import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.UserDao;
//import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BudgetUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    private final UserDao userDao;
//
//    @Autowired
//    public BudgetUserDetailsService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        final User user = userDao.findByEmail(email);
//        if (user == null) {
//            throw  new NotFoundException("User with email {"+ email +"} was not found!");
//        }
//        return new BudgetUserDetails(user);
//    }
//}
