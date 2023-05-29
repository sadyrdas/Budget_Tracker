package cz.cvut.fel.nss.budgetmanager.BudgetManager.dto;


import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Currency;

import java.math.BigDecimal;

public class WalletResponseDTO {
    private Long walletId;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal budgetLimit;
    private String clientEmail;
    private String name;

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(BigDecimal budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

