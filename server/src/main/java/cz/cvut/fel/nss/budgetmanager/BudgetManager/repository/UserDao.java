package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User findByEmail(String email){
        try {
            return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email )
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Transactional
    public Boolean deleteUserByEmail(String email) {
        try {
            int rowUpdated =  em.createNamedQuery("User.deleteByEmail", User.class)
                    .setParameter("email", email).executeUpdate();

            return rowUpdated != 0;
        } catch (NoResultException e) {
            return null;
        }

    }




}
