package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a wallet entity in the system.
 */
@Entity
@Table(name = "wallet")
@NamedQueries({
        @NamedQuery(name = "findByClientEmail", query = "SELECT w FROM Wallet w WHERE w.client.email =:email")
})
public class Wallet implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id")
    private Long walletId;

    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
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

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "goal")
    @Column(name = "money_goal")
    @CollectionTable(name = "goals", joinColumns = @JoinColumn(name = "wallet_id"))
    private Map<String, BigDecimal> budgetGoal;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "wallet")
    private List<Transaction> transactions;

    public Wallet() {
    }

    public Long getWalletId() {
        return walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null){
            this.amount = amount;
        }
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        if (currency != null){
            this.currency = currency;
        }
    }

    public BigDecimal getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(BigDecimal budgetLimit) {
        if (budgetLimit != null){
            this.budgetLimit = budgetLimit;
        }
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
        if (name != null){
            this.name = name;
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        Objects.requireNonNull(transaction);
        if (transactions == null) transactions = new ArrayList<>();
        transactions.add(transaction);
    }

    public Map<String, BigDecimal> getBudgetGoal() {
        return budgetGoal;
    }

    public void setBudgetGoal(String key, BigDecimal money) {
        this.budgetGoal.put(key, money);
    }
}
