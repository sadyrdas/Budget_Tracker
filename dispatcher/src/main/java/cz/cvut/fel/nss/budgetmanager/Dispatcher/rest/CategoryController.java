package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.UserDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest/categories")
public class CategoryController {

    @Autowired
    private RestTemplate restTemplate;

    private final String server2Url = "http://localhost:8082/rest/categories";

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@test.test", "admin");
        HttpEntity<Category> request = new HttpEntity<>(category, headers);
        return restTemplate.exchange(server2Url, HttpMethod.POST, request, Category.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        String url = server2Url + "/{id}";
        HttpEntity<Category> requestEntity = new HttpEntity<>(updatedCategory);

        ResponseEntity<Category> response = restTemplate.exchange(url,
                HttpMethod.PUT,
                requestEntity,
                Category.class,
                id);

        System.out.println(response);
        return response;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        String url = server2Url + "/{id}";
        ResponseEntity<Void> response = restTemplate.exchange(url,
                HttpMethod.DELETE,
                null,
                Void.class,
                id);
        System.out.println(response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        String url = server2Url +"/{id}";
        ResponseEntity<Category> response = restTemplate.getForEntity(url,
                Category.class,
                id);
        System.out.println(response);
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return restTemplate.exchange(
                server2Url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>(){});
    }
}

