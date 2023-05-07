package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import org.springframework.stereotype.Repository;

@Repository
public class WalletDao extends BaseDao<Wallet>{
    /**
     * Constructs a new BaseDao instance with the given type parameter.
     *
     * @param type the class type of the entity managed by this BaseDao.
     */
    public WalletDao(Class<Wallet> type) {
        super(type);
    }
}
