package cz.cvut.fel.nss.budgetmanager.Dispatcher.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Wallet{
    private Long walletId;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal budgetLimit;
    private User client;
    private String name;
    private Map<String, BigDecimal> budgetGoal;
    private List<Transaction> transactions;

    public Wallet() {
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null){
            this.amount = amount;
        }
    }

    public void setCurrency(Currency currency) {
        if (currency != null){
            this.currency = currency;
        }
    }

    public void setBudgetLimit(BigDecimal budgetLimit) {
        if (budgetLimit != null){
            this.budgetLimit = budgetLimit;
        }
    }

    public void setName(String name) {
        if (name != null){
            this.name = name;
        }
    }

    public void addTransaction(Transaction transaction){
        Objects.requireNonNull(transaction);
        if (transactions == null) transactions = new ArrayList<>();
        transactions.add(transaction);
    }
}
