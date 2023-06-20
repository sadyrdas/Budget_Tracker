package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a transaction entity in the system.
 */
@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT t FROM Transaction t where t.description = :name ")
})
@Document(indexName = "trans")
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactions_id")
    private Long transId;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(name = "trans_date", nullable = false, columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal money;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "wallet", referencedColumnName = "wallet_id")
    private Wallet wallet;

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "name")
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
        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (date != null){
            this.date = date;
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        if (money != null) {
            this.money = money;
        }
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        if (typeTransaction != null){
            this.typeTransaction = typeTransaction;
        }
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
        if (category != null) {
            this.category = category;
        }
    }

    /**
     * Represents a builder for constructing a Transaction object.
     */
    public static class Builder {
        private Category category;
        private Wallet wallet;
        private String description;
        private TypeTransaction typeTransaction;
        private BigDecimal money;
        private LocalDateTime transDate;

        /**
         * Sets the category for the transaction.
         *
         * @param category The category to set.
         * @return The builder instance.
         * @throws NotFoundException if the category is null.
         */
        public Builder category(Category category) {
            if (category == null) {
                throw new NotFoundException("Category can't be null");
            }
            this.category = category;
            return this;
        }

        /**
         * Sets the wallet for the transaction.
         *
         * @param wallet The wallet to set.
         * @return The builder instance.
         */
        public Builder wallet(Wallet wallet) {
            this.wallet = wallet;
            return this;
        }


        /**
         * Sets the name/description for the transaction.
         *
         * @param name The name/description to set.
         * @return The builder instance.
         * @throws NotFoundException if the name/description is null.
         */
        public Builder name(String name) {
            if (name == null) {
                throw new NotFoundException("Transaction name can't be null");
            }
            this.description = name;
            return this;
        }

        /**
         * Sets the type of transaction.
         *
         * @param typeTransaction The type of transaction to set.
         * @return The builder instance.
         * @throws NotFoundException if the type of transaction is null.
         */
        public Builder typeTransaction(TypeTransaction typeTransaction) {
            if (typeTransaction == null) {
                throw new NotFoundException("Transaction type can't be null");
            }
            this.typeTransaction = typeTransaction;
            return this;
        }

        /**
         * Sets the money involved in the transaction.
         *
         * @param money The amount of money to set.
         * @return The builder instance.
         * @throws NotFoundException if the money amount is null.
         */
        public Builder money(BigDecimal money, TypeTransaction type) {
            if (money == null) {
                throw new NotFoundException("Money can't be null");
            }
            if (type.getType().equals("INCOME")){
                wallet.setAmount(wallet.getAmount().add(money));
            } else{
                wallet.setAmount(wallet.getAmount().subtract(money));
            }
            this.money = money;
            return this;
        }

        /**
         * Sets the transaction date as the current date and time.
         *
         * @return The builder instance.
         * @throws NotFoundException if the category is null.
         */
        public Builder transDate() {
            if (category == null) {
                throw new NotFoundException("Date can't be null");
            }
            this.transDate = LocalDateTime.now();
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.category = this.category;
            transaction.wallet = this.wallet;
            transaction.description = this.description;
            transaction.typeTransaction = this.typeTransaction;
            transaction.money = this.money;
            transaction.date = this.transDate;
            // Set other attributes
            return transaction;
        }
    }
}