package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing categories.
 */
@RestController
@RequestMapping("rest/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Constructs a new CategoryController with the provided CategoryService.
     *
     * @param categoryService The CategoryService to be used.
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Creates a new category.
     *
     * @param category The category to create.
     * @return The ResponseEntity containing the created category and the appropriate status.
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    /**
     * Updates an existing category.
     *
     * @param id              The ID of the category to update.
     * @param updatedCategory The updated category object.
     * @return The ResponseEntity containing the updated category and the appropriate status.
     * @throws NotFoundException if the category with the specified ID is not found.
     */
    @PutMapping("/{id}")
//    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        Category category = categoryService.getCategory(id);

        if (category == null) {
            throw new NotFoundException("Category with id: " + id + " was not found");
        }

        category.setName(updatedCategory.getName());
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    /**
     * Deletes a category.
     *
     * @param id The ID of the category to delete.
     * @return The ResponseEntity with no content and the appropriate status.
     */
    @DeleteMapping("/{id}")
//    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The ResponseEntity containing the retrieved category and the appropriate status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);

        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all categories.
     *
     * @return The ResponseEntity containing the list of all categories and the appropriate status.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

}
