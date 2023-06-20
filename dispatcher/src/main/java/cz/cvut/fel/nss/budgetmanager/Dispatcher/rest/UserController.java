package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.UserDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.User;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.security.model.AuthenticationRequest;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.security.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("rest/user")
public class UserController {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    @Autowired
    public UserController(RestTemplate restTemplate, @Value("${server1.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@test.test", "admin");
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
        ResponseEntity<Void> response = restTemplate.exchange(serverUrl + "/register", HttpMethod.POST, request, Void.class);
        System.out.println(response);
        return response;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id) {
        ResponseEntity<User> response = restTemplate.getForEntity(serverUrl + "/" + id, User.class);
        return response.getBody();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        HttpEntity<AuthenticationRequest> req = new HttpEntity<>(request);
        return restTemplate.postForEntity(serverUrl + "/authenticate", req, AuthenticationResponse.class);
    }

}