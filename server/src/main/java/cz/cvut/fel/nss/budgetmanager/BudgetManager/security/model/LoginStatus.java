package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.model;

public class LoginStatus {
    private boolean isLogged;
    private String email;
    private String errorMessage;
    private boolean success;

    public LoginStatus() {}

    public LoginStatus(boolean isLogged, String email, String errorMessage, boolean success) {
        this.isLogged = isLogged;
        this.email = email;
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
