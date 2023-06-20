package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.util;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.AuthenticationToken;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import java.util.Objects;

/**
 * Utility class for security-related operations and access to the current user and user details.
 */
public class SecurityUtils {

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return The User object representing the currently authenticated user.
     * @throws NullPointerException if the security context or authentication details are null.
     */
    public static User getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        Objects.requireNonNull(context);
        final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) context.getAuthentication().getDetails();
        return budgetUserDetails.getUser();
    }

    /**
     * Retrieves the user details of the currently authenticated user from the security context.
     *
     * @return The BudgetUserDetails object representing the user details, or null if not available or not of the expected type.
     */
    public static BudgetUserDetails getUserDetails() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().getDetails() instanceof BudgetUserDetails) {
            return (BudgetUserDetails) context.getAuthentication().getDetails();
        }
        return null;
    }

    /**
     * Sets the currently authenticated user and authentication token in the security context.
     *
     * @param budgetUserDetails The BudgetUserDetails representing the authenticated user.
     * @return The AuthenticationToken representing the authenticated user.
     */
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
