package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT t FROM Transaction t where t.description = :name ")
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactions_id")
    private Long transId;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(name = "trans_date", nullable = false)
    private LocalDateTime date;

    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "wallet")
    private Wallet wallet;

    @OneToOne
    @JoinColumn(name = "category")
    private Category category;

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static class Builder {
        private Category category;
        private Wallet wallet;
        private String description;
        private TypeTransaction typeTransaction;
        private Currency currency;
        private BigDecimal money;
        private LocalDateTime transDate;
        // Other attributes

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder wallet(Wallet wallet) {
            this.wallet = wallet;
            return this;
        }

        public Builder name(String name) {
            this.description = name;
            return this;
        }

        public Builder typeTransaction(TypeTransaction typeTransaction) {
            this.typeTransaction = typeTransaction;
            return this;
        }

        public Builder currency() {
            this.currency = wallet.getCurrency();
            return this;
        }

        public Builder money(BigDecimal money){
            this.money = money;
            return this;
        }

        public Builder transDate(){
            this.transDate = LocalDateTime.now();
            return this;
        }

        // Methods to set other attributes

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.category = this.category;
            transaction.wallet = this.wallet;
            transaction.description = this.description;
            transaction.typeTransaction = this.typeTransaction;
            currency = wallet.getCurrency(); // TODO check if we need add atr currency into trans action
            transaction.money = this.money;
            transaction.date = this.transDate;
            // Set other attributes
            return transaction;

        }
    }
}