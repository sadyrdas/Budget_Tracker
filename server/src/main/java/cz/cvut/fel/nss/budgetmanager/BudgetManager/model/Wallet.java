package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "wallet")
public class Wallet{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id")
    private Long walletId;

    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal amount;

    @Transient
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Basic(optional = false)
    @Column(name = "budget_limit")
    private BigDecimal budgetLimit;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="client", referencedColumnName = "email")
    private User client;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @ElementCollection
    @MapKeyColumn(name = "goal")
    @Column(name = "money_goal")
    @CollectionTable(name = "goals")
    private Map<String, BigDecimal> budgetGoal;

    @Transient
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    public Wallet() {
    }

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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        Objects.requireNonNull(transaction);
        if (transactions == null) transactions = new ArrayList<>();
        transactions.add(transaction);
    }

    public Map<String, BigDecimal> getBudgetGoal() {
        return budgetGoal;
    }

    public void setBudgetGoal(Map<String, BigDecimal> budgetGoal) {
        this.budgetGoal = budgetGoal;
    }
}
