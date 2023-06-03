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

@Service
public class AuthenticationSuccess implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    private final ObjectMapper mapper;

    @Autowired
    public AuthenticationSuccess(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private String getEmail(Authentication authentication) {
        if (authentication == null) {
            return "";
        }
        return ((BudgetUserDetails) authentication.getPrincipal()).getUsername();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final String email = getEmail(authentication);
        final LoginStatus loginStatus = new LoginStatus(true, email, null, authentication.isAuthenticated());
        mapper.writeValue(response.getOutputStream(), loginStatus);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        final LoginStatus loginStatus = new LoginStatus(false, null, null, true);
        mapper.writeValue(response.getOutputStream(), loginStatus);
    }
}
