package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT t FROM Transaction t where t.name = :name ")
})
public class Transaction extends AbstractEntity {


    @ManyToOne
    private Category category;

    @ManyToOne
    private Wallet walletId;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;



    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal money;

    private TypeTransaction typeTransaction;

    public Transaction(Category category, Wallet walletId, String name, BigDecimal money) {
        this.category = category;
        this.walletId = walletId;
        this.name = name;
        this.money = money;
    }

    public Transaction() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Wallet getWalletId() {
        return walletId;
    }

    public void setWalletId(Wallet walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
