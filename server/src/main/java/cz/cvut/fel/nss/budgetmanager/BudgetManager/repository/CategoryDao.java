package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;

public class CategoryDao extends BaseDao<Category> {
    /**
     * Constructs a new BaseDao instance with the given type parameter.
     *
     * @param type the class type of the entity managed by this BaseDao.
     */
    public CategoryDao(Class<Category> type) {
        super(type);
    }
}
