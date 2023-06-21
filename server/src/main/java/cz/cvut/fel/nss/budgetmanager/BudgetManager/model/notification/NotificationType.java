package cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification;

/**
 Enum representing different types of notifications.
 */
public enum NotificationType {
    BUDGET_OVERLIMIT("Budget Over-limit"),
    TRANSACTION_CREATED("Transaction Created"),
    SAVINGS_TARGET_REACHED("Savings Target Reached");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
