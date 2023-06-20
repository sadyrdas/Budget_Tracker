package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.CategoryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Service class for managing categories.
 */
@Slf4j
@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    /**
     * Constructs a new CategoryService with the provided CategoryDao.
     *
     * @param categoryDao The CategoryDao implementation used for data access.
     */
    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    /**
     * Creates a new category.
     *
     * @param category The category to create.
     */
    @Transactional
    public void createCategory(Category category) {
        categoryDao.persist(category);
    }

    /**
     * Updates an existing category.
     *
     * @param category The updated category.
     * @return The updated category.
     */
    @Transactional
    @CachePut(value = "categories", key = "#category.getCategoryId()")
    public Category updateCategory(Category category) {
        categoryDao.update(category);
        return category;
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     */
    @Transactional
    @CacheEvict(value = "categories", key = "#id")
    public void deleteCategory(Long id) {
        Category category = categoryDao.find(id);
        if (category == null) {
            throw new NotFoundException("Category with id " + id + " was not found");
        }
        categoryDao.remove(category);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The retrieved category.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "categories", key = "#id")
    public Category getCategory(Long id) {
        Objects.requireNonNull(id);
        log.info("Fetching the category {} from DB", id);
        return categoryDao.find(id);
    }

    /**
     * Retrieves all categories.
     *
     * @return The list of all categories.
     */
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name The name of the category.
     * @return The retrieved category.
     */
    @Transactional(readOnly = true)
    public Category getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }
}