package cz.cvut.fel.nss.budgetmanager.BudgetManager.security;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service.AuthenticationService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationToken;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.util.SecurityUtils;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.security.BudgetUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    private final BudgetUserDetailsService budgetUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Autowired
    public DefaultAuthenticationProvider(BudgetUserDetailsService budgetUserDetailsService,
                                         PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.budgetUserDetailsService = budgetUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getPrincipal().toString();
        final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) budgetUserDetailsService.loadUserByUsername(email);
        final String password = (String)authentication.getCredentials();

        if (!passwordEncoder.matches(password, budgetUserDetails.getPassword())) {
            throw new BadCredentialsException("Provided credentials do not matches!");
        }

        authenticationService.authenticate(email);
        return SecurityUtils.setCurrentUser(budgetUserDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication) ||
                AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
