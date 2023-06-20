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

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("rest/user")
public class UserController {
    private final UserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * Constructs a new UserController with the provided UserService.
     *
     * @param userService The UserService to be used.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param user The user object containing the registration details.
     * @return The ResponseEntity with the appropriate status and headers.
     * @throws UserAlreadyExists if a user with the same email already exists.
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({UserAlreadyExists.class})
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

    /**
     * Retrieves a user by email.
     *
     * @param userEmail The email of the user to retrieve.
     * @return The User object with the specified email.
     * @throws NotFoundException if a user with the specified email is not found.
     */
    @GetMapping(value = "/getUserByEmail" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public User getUser(@RequestParam("email") String userEmail) {
        User u = userService.findUserByEmail(userEmail);
        if (u == null){
            throw new NotFoundException("User with that email" + userEmail + " doesn't exist");
        }
        return u;
    }
}
