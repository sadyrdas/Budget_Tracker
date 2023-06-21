package cz.cvut.fel.nss.budgetmanager.BudgetManager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.LoginStatus;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * Custom implementation of the AuthenticationSuccessHandler and LogoutSuccessHandler interfaces.
 * Handles authentication success and logout success events by returning a success response with the appropriate status and information.
 */
@Service
public class AuthenticationSuccess implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    private final ObjectMapper mapper;

    /**
     * Constructs a new AuthenticationSuccess instance with the provided ObjectMapper.
     *
     * @param mapper The ObjectMapper to be used for JSON serialization.
     */
    @Autowired
    public AuthenticationSuccess(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves the email of the authenticated user from the Authentication object.
     *
     * @param authentication The Authentication object representing the authentication information.
     * @return The email of the authenticated user, or an empty string if not available.
     */
    private String getEmail(Authentication authentication) {
        if (authentication == null) {
            return "";
        }
        return ((BudgetUserDetails) authentication.getPrincipal()).getUsername();
    }

    /**
     * Handles the authentication success event by writing a success response with the appropriate status and information.
     *
     * @param request        The HttpServletRequest object.
     * @param response       The HttpServletResponse object.
     * @param authentication The Authentication object representing the authentication result.
     * @throws IOException      if an I/O error occurs during the response writing.
     * @throws ServletException if an error occurs while handling the authentication success.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final String email = getEmail(authentication);
        final LoginStatus loginStatus = new LoginStatus(true, email, null, authentication.isAuthenticated());
        mapper.writeValue(response.getOutputStream(), loginStatus);
    }

    /**
     * Handles the logout success event by writing a success response with the appropriate status and information.
     *
     * @param request        The HttpServletRequest object.
     * @param response       The HttpServletResponse object.
     * @param authentication The Authentication object representing the authenticated user (before logout).
     * @throws IOException      if an I/O error occurs during the response writing.
     * @throws ServletException if an error occurs while handling the logout success.
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        final LoginStatus loginStatus = new LoginStatus(false, null, null, true);
        mapper.writeValue(response.getOutputStream(), loginStatus);
    }
}
