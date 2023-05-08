package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SubCategory extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    public SubCategory(String name) {
        this.name = name;
    }

    public SubCategory() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
