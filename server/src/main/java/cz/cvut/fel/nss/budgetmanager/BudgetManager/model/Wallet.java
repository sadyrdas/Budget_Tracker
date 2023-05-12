package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "wallet")
public class Wallet extends AbstractEntity{


    @Id
    private Integer id; // ????

    @Transient
    public static Wallet instance;

    private BigDecimal amount;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    @OneToOne
    @JoinColumn()
    private User userId;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal budgetLimit;
    private BigDecimal savingsGoal;

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

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public void setTransactions(Transaction transaction){
        transactions.add(transaction);
    }

    public BigDecimal getBudgetLimit(){
        return budgetLimit;
    }

    public void setBudgetLimit(BigDecimal amount){
        this.budgetLimit = amount;
    }
}
