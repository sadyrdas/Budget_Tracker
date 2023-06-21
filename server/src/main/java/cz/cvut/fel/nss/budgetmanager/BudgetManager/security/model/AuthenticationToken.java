package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.security.Principal;
import java.util.Collection;

/**
 * Custom implementation of the AuthenticationToken class that represents an authentication token for a user.
 * Extends AbstractAuthenticationToken and implements the Principal interface.
 */
public class AuthenticationToken extends AbstractAuthenticationToken implements Principal {
    private final BudgetUserDetails budgetUserDetails;

    /**
     * Constructs a new AuthenticationToken with the provided authorities and BudgetUserDetails.
     *
     * @param authorities      The collection of GrantedAuthority objects representing the authorities assigned to the user.
     * @param budgetUserDetails The BudgetUserDetails representing the user's details.
     */
    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, BudgetUserDetails budgetUserDetails) {
        super(authorities);
        this.budgetUserDetails = budgetUserDetails;
        super.setAuthenticated(true);
        super.setDetails(budgetUserDetails);
    }

    /**
     * Retrieves the user's credentials (password).
     *
     * @return The user's password.
     */
    @Override
    public String getCredentials() {
        return budgetUserDetails.getPassword();
    }

    /**
     * Retrieves the user's principal details (BudgetUserDetails).
     *
     * @return The BudgetUserDetails representing the user's principal.
     */
    @Override
    public BudgetUserDetails getPrincipal() {
        return budgetUserDetails;
    }
}
