package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model;

/**
 * Represents the login status after an authentication attempt.
 * Contains information about whether the login was successful, the user's email, error message (if any), and login status.
 */
public class LoginStatus {
    private boolean isLogged;
    private String email;
    private String errorMessage;
    private boolean success;


    /**
     * Default constructor for LoginStatus.
     */
    public LoginStatus() {}

    /**
     * Constructs a new LoginStatus with the provided values.
     *
     * @param isLogged     Whether the login was successful.
     * @param email        The user's email.
     * @param errorMessage The error message (if any).
     * @param success      Whether the login attempt was successful.
     */
    public LoginStatus(boolean isLogged, String email, String errorMessage, boolean success) {
        this.isLogged = isLogged;
        this.email = email;
        this.errorMessage = errorMessage;
        this.success = success;
    }

    /**
     * Checks whether the login was successful.
     *
     * @return true if the login was successful, false otherwise.
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Sets whether the login was successful.
     *
     * @param logged true if the login was successful, false otherwise.
     */
    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    /**
     * Retrieves the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email The user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage The error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Checks whether the login attempt was successful.
     *
     * @return true if the login attempt was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets whether the login attempt was successful.
     *
     * @param success true if the login attempt was successful, false otherwise.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
