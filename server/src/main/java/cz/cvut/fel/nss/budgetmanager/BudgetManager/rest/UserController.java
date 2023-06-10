package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.UserAlreadyExists;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.rest.util.RestUtil;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/user")
public class UserController {
    private final UserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody User user) {
        Boolean result = userService.createUser( user.getEmail(), user.getUsername(),
                user.getPassword());
        if (!result) {
            throw new UserAlreadyExists("User with that email " + user.getEmail() + " already exists");
        }
        LOG.debug("User with email {} successfully registered.", user.getEmail());
        final HttpHeaders headers = RestUtil.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserByEmail" , produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestParam("email") String userEmail) {
        User u = userService.findUserByEmail(userEmail);
        LOG.debug("Found user with email {}", userEmail);
        if (u == null){
            throw new NotFoundException("User with that email" + userEmail + " doesn't exist");
        }
        return u;
    }

}
