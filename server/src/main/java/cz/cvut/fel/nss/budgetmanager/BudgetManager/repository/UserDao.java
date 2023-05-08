package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User>{
    /**
     * Constructs a new BaseDao instance with the given type parameter.
     *
     * @param type the class type of the entity managed by this BaseDao.
     */
    public UserDao(Class<User> type) {
        super(type);
    }

    public User findByUsername(String username){
        try {
            return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username )
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
