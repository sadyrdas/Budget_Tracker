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

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenValidatorService tokenValidatorService;
    private final BudgetUserDetailsService budgetUserDetailsService;
    private final PasswordEncoder passwordEncoder;
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

        String[] tokenParts = token.split("\\|");

        if (tokenParts.length != 3) {
            filterChain.doFilter(request, response);
            return;
        }

        String hash = tokenParts[0];
        String email = tokenParts[1];
        LocalDateTime expirationDate = LocalDateTime.parse(tokenParts[2]);

        if (!hash.isEmpty() && !email.isEmpty() && tokenValidatorService.validateToken(hash, email, expirationDate)) {
            final BudgetUserDetails budgetUserDetails = (BudgetUserDetails) budgetUserDetailsService.loadUserByUsername(email);
            final String password = budgetUserDetails.getPassword();

            if (!passwordEncoder.matches(password, budgetUserDetails.getPassword())) {
                throw new BadCredentialsException("Provided credentials do not matches!");
            }

            authenticationService.authenticate(email);
            SecurityUtils.setCurrentUser(budgetUserDetails);
            filterChain.doFilter(request, response);

        }
    }
}
