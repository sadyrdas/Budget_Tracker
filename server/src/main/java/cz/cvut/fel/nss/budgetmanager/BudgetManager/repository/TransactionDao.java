package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionDao extends BaseDao<Transaction>{
    /**
     * Constructs a new BaseDao instance with the given type parameter.
     *
     * @param type the class type of the entity managed by this BaseDao.
     */
    public TransactionDao(Class<Transaction> type) {
        super(type);
    }

    public List<Transaction> findByCategory(Category category){
        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.category = :category",
                Transaction.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<Transaction> findByAmount(BigDecimal amount) {
        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.money = :amount",
                Transaction.class);
        query.setParameter("amount", amount);
        return query.getResultList();
    }

    public List<Transaction> findByMessage(String message) {
        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.name = :message", Transaction.class);
        query.setParameter("message", message);
        return query.getResultList();
    }

//    public List<Transaction> findByDate(LocalDate date) {
//        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.date = :date", Transaction.class);
//        query.setParameter("date", date);
//        return query.getResultList();
//    }

//    public List<Transaction> getTransactionsWithinInterval(LocalDate startDate, LocalDate endDate) {
//        EntityManager em = getEntityManager();
//        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.date >= :startDate AND t.date <= :endDate", Transaction.class);
//        query.setParameter("startDate", startDate);
//        query.setParameter("endDate", endDate);
//        return query.getResultList();
//    }

    /**
     * KDE DATY U TRANZAKCII
     */

}
