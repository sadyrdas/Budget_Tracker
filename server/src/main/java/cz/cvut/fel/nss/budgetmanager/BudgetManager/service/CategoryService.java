package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {


    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public void createCategory(Category category) {
        categoryDao.persist(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    @Transactional
    public void deleteCategory(Category category) {
        categoryDao.remove(category);
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }
}