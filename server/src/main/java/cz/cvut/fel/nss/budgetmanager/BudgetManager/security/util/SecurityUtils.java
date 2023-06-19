package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.util;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationToken;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Objects;

public class SecurityUtils {

    public static User getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        Objects.requireNonNull(context);
        final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) context.getAuthentication().getDetails();
        return budgetUserDetails.getUser();
    }

    public static BudgetUserDetails getUserDetails() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().getDetails() instanceof BudgetUserDetails) {
            return (BudgetUserDetails) context.getAuthentication().getDetails();
        }
        return null;
    }

    public static AuthenticationToken setCurrentUser(BudgetUserDetails budgetUserDetails) {
        final AuthenticationToken token = new AuthenticationToken(budgetUserDetails.getAuthorities(), budgetUserDetails);
        token.setAuthenticated(true);
        System.out.println(token.getPrincipal());
        final SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
        return token;
    }
}
