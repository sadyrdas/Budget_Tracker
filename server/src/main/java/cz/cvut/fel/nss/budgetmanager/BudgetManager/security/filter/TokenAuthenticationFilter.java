package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.filter;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model.BudgetUserDetails;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service.AuthenticationService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.service.TokenValidatorService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.util.SecurityUtils;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.security.BudgetUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenValidatorService tokenValidatorService;
    private final BudgetUserDetailsService budgetUserDetailsService;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(request.getServletPath());
        if (request.getServletPath().contains("/rest/user/authenticate")
                || request.getServletPath().contains("/rest/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        String hash = token;

        if (!hash.isEmpty() && tokenValidatorService.validateToken(hash)) {
            final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) budgetUserDetailsService.loadUserByUsername(hash);

            authenticationService.authenticate(hash);
            SecurityUtils.setCurrentUser(budgetUserDetails);
            filterChain.doFilter(request, response);

        }
    }
}
