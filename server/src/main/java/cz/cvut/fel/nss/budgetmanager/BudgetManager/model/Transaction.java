package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT t FROM Transaction t where t.name = :name ")
})
public class Transaction extends AbstractEntity {

    @Id
    private Long transID;

    /**
     * KDE DATY U TRANZAKCII?
     */

    @ManyToOne
    private Category category;

    @ManyToOne
    private Wallet walletId;

    @ManyToOne
    private Wallet wallet;
    //Check if relation is good!

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;



    @Basic(optional = false)
    @Column(nullable = false)
    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    public Transaction(Category category, Wallet wallet, String name, BigDecimal money, TypeTransaction typeTransaction) {
        this.category = category;
        this.wallet = wallet;
        this.name = name;
        this.money = money;
        this.typeTransaction = typeTransaction;
    }

    public Transaction() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
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

    public void setTypeTransaction(TypeTransaction type){
        this.typeTransaction = type;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public Long getTransID() {
        return transID;
    }
}
