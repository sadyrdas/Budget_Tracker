package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.UserAlreadyExists;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.security.AuthTokenDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.rest.util.RestUtil;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationRequest;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationResponse;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * REST controller for managing user-related operations.
 */
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("rest/user")
public class UserController {

    private final UserService userService;
    private final AuthTokenDao authTokenDao;

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
        log.debug("User with email {} successfully registered.", user.getEmail());
        final HttpHeaders headers = RestUtil.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Retrieves a user by email.
     *
     * @param id The id of the user to retrieve.
     * @return The User object with the specified email.
     * @throws NotFoundException if a user with the specified email is not found.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public User getUser(@PathVariable Long id) {
        User u = userService.findUser(id);
        if (u == null){
            throw new NotFoundException("User not found");
        }
        return u;
    }

    /**
     * Authenticates a user.
     *
     * @param request The AuthenticationRequest object containing user credentials.
     * @return The ResponseEntity containing the AuthenticationResponse.
     */
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        User user = userService.findUserByEmail(request.getEmail());

        if (user == null) {
            return new ResponseEntity<>(new AuthenticationResponse("", ""),
                    HttpStatus.UNAUTHORIZED);
        }

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime futureExpireTokenTime = currentTime.plusHours(8);

        log.info(String.format("A new token for user %s was set.", user.getEmail()));
        log.info(String.format("Token will expire in: %s", futureExpireTokenTime));

        authTokenDao.insertToken(request.getEmail(), user.getClientId(), futureExpireTokenTime);

        return new ResponseEntity<>(
                new AuthenticationResponse(
                        request.getEmail(), ""), HttpStatus.OK);
    }
}
