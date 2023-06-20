package cz.cvut.fel.nss.budgetmanager.BudgetManager.security;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationToken;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.security.BudgetUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the AuthenticationProvider interface.
 * Provides authentication functionality by verifying the provided credentials against the user details.
 */
@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    private final BudgetUserDetailsService budgetUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new DefaultAuthenticationProvider with the provided BudgetUserDetailsService and PasswordEncoder.
     *
     * @param budgetUserDetailsService The BudgetUserDetailsService to be used for user details retrieval.
     * @param passwordEncoder          The PasswordEncoder to be used for password encoding and verification.
     */
    @Autowired
    public DefaultAuthenticationProvider(BudgetUserDetailsService budgetUserDetailsService,
                                         PasswordEncoder passwordEncoder) {
        this.budgetUserDetailsService = budgetUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates the provided authentication object by verifying the credentials against the user details.
     *
     * @param authentication The authentication object containing the user's credentials.
     * @return The authenticated Authentication object representing the user's authentication result.
     * @throws AuthenticationException if the provided credentials do not match.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getPrincipal().toString();
        final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) budgetUserDetailsService.loadUserByUsername(email);
        final String password = (String)authentication.getCredentials();

        if (!passwordEncoder.matches(password, budgetUserDetails.getPassword())) {
            throw new BadCredentialsException("Provided credentials do not matches!");
        }
        return SecurityUtils.setCurrentUser(budgetUserDetails);
    }

    /**
     * Checks if the authentication provider supports the given authentication class.
     *
     * @param authentication The authentication class to be checked.
     * @return true if the authentication provider supports the class, false otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication) ||
                AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
