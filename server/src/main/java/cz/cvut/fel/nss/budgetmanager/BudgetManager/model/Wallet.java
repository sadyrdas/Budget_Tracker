package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet extends AbstractEntity{

    @Transient
    public static Wallet instance;

    private BigDecimal amount;

    @OneToOne
    @JoinColumn()
    private User userId;

    private Currency currency;



    public Wallet(BigDecimal amount, User userId) {
        this.amount = amount;
        this.userId = userId;
    }

    public Wallet() {
    }

    public static Wallet getInstance() {
        if (instance == null){
            instance = new Wallet();
        }
        return instance;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
}
