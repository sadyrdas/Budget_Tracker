package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        Category category = categoryService.getCategory(id);

        if (category == null) {
            throw new NotFoundException("Category with id: " + id + " was not found");
        }

        category.setName(updatedCategory.getName());
        categoryService.updateCategory(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        if (category == null) {
            throw new NotFoundException("Category with id: " + id + " was not found");
        }
        categoryService.deleteCategory(category);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);

        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

}
