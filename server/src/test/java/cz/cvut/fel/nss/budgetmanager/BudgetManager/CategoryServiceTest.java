package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.CategoryDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        categoryService.createCategory(category);

        verify(categoryDao, times(1)).persist(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        when(categoryDao.update(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(category);

        assertEquals(category, updatedCategory);
    }

    @Test
    public void testGetCategory() {
        Category category = new Category();
        when(categoryDao.find(anyLong())).thenReturn(category);

        Category foundCategory = categoryService.getCategory(1L);

        assertEquals(category, foundCategory);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryDao.findAll()).thenReturn(categories);

        List<Category> foundCategories = categoryService.getAllCategories();

        assertEquals(categories, foundCategories);
    }

    @Test
    public void testGetCategoryByName() {
        Category category = new Category();
        when(categoryDao.getCategoryByName(anyString())).thenReturn(category);

        Category foundCategory = categoryService.getCategoryByName("test");

        assertEquals(category, foundCategory);
    }
}
