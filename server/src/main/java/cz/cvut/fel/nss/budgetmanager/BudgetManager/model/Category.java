package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "category")
public class Category extends AbstractEntity {
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @OneToMany
    private Set<SubCategory> subCategory;


    public Category(String name) {
        this.name = name;

    }

    public Category() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Set<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }
}
