package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken implements Principal {
    private final BudgetUserDetails budgetUserDetails;

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, BudgetUserDetails budgetUserDetails) {
        super(authorities);
        this.budgetUserDetails = budgetUserDetails;
        super.setAuthenticated(true);
        super.setDetails(budgetUserDetails);
    }

    @Override
    public String getCredentials() {
        return budgetUserDetails.getPassword();
    }

    @Override
    public BudgetUserDetails getPrincipal() {
        return budgetUserDetails;
    }
}
