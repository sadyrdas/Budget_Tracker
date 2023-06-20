package cz.cvut.fel.nss.budgetmanager.BudgetManager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.LoginStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * Custom implementation of the AuthenticationFailureHandler interface.
 * Handles authentication failure events by returning an error response with the appropriate status and message.
 */
@Service
public class AuthenticationFailure implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    @Autowired
    public AuthenticationFailure(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        final LoginStatus loginStatus = new LoginStatus(false, null, exception.getMessage(), false);
        mapper.writeValue(response.getOutputStream(), loginStatus);
    }
}
