package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

/**
 * Custom implementation of the UserDetails interface that represents the details of a budget user.
 * Provides user-specific information for authentication and authorization.
 */
public class BudgetUserDetails implements UserDetails {

    private final Set<GrantedAuthority> authorities;
    private final transient User user;


    /**
     * Constructs a new BudgetUserDetails with the provided User object.
     *
     * @param user The User object representing the budget user.
     */
    public BudgetUserDetails(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }

    /**
     * Adds the "USER" role to the user's authorities.
     */
    public void addUserRole() {
        authorities.add(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Retrieves the authorities assigned to the user.
     *
     * @return A collection of GrantedAuthority objects representing the user's authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the user's email (username).
     *
     * @return The user's email.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Checks if the user's account is not expired.
     *
     * @return true if the user's account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * Checks if the user's account is not locked.
     *
     * @return true if the user's account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * Checks if the user's credentials are not expired.
     *
     * @return true if the user's credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * Checks if the user's account is enabled.
     *
     * @return true if the user's account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * Retrieves the User object associated with the BudgetUserDetails.
     *
     * @return The User object representing the budget user.
     */
    public User getUser() {
        return user;
    }
}
