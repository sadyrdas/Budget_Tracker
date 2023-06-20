package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("rest/categories")
public class CategoryController {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    @Autowired
    public CategoryController(RestTemplate restTemplate, @Value("${server2.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        HttpEntity<Category> request = new HttpEntity<>(category);
        return restTemplate.exchange(serverUrl, HttpMethod.POST, request, Category.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        String url = serverUrl + "/{id}";
        HttpEntity<Category> requestEntity = new HttpEntity<>(updatedCategory);

        return restTemplate.exchange(url,
                HttpMethod.PUT,
                requestEntity,
                Category.class,
                id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        String url = serverUrl + "/{id}";
        return restTemplate.exchange(url,
                HttpMethod.DELETE,
                null,
                Void.class,
                id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        String url = serverUrl +"/{id}";
        ResponseEntity<Category> response = restTemplate.getForEntity(url,
                Category.class,
                id);
        System.out.println(response);
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
    }
}

