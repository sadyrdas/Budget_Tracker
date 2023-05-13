package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.UserAlreadyExists;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.rest.util.RestUtil;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody User user) {
        Boolean result = userService.createUser(user.getClientId(), user.getEmail(), user.getUsername(),
                user.getPassword());
        if (!result) {
            throw new UserAlreadyExists("User with that email " + user.getEmail() + " already exists");
        }
        System.out.println("User with email" + " " + user.getEmail() + " " +  "successfully registered.");
        final HttpHeaders headers = RestUtil.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/getUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestBody User user) {
        User u = userService.findUserByEmail(user.getEmail());
        System.out.println("Found user with email" + " " + user.getEmail());
        if (u == null){
            throw new NotFoundException("User with that email" + user.getEmail() + " doesn't exist");
        }
        return u;
    }

}
