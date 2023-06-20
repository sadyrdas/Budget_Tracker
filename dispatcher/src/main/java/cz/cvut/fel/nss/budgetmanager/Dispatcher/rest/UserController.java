package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("rest/user")
public class UserController {
    private final RestTemplate restTemplate;
    private final String server1Url;

    @Autowired
    public UserController(RestTemplate restTemplate, @Value("${server1.url}") String server1Url) {
        this.restTemplate = restTemplate;
        this.server1Url = server1Url;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@test.test", "admin");
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
        ResponseEntity<Void> response = restTemplate.exchange(server1Url + "/register", HttpMethod.POST, request, Void.class);
        System.out.println(response);
        return response;
    }
}