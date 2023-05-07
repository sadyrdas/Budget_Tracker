package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;

public class TransactionDao extends BaseDao<Transaction>{
    /**
     * Constructs a new BaseDao instance with the given type parameter.
     *
     * @param type the class type of the entity managed by this BaseDao.
     */
    public TransactionDao(Class<Transaction> type) {
        super(type);
    }
}
