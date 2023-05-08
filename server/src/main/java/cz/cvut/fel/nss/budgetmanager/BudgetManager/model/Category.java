package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "findCategoryByName", query = "SELECT c FROM Category c WHERE c.name = :name "),
        @NamedQuery(name = "updateCategoryByName", query = "UPDATE Category set name = name where Category.name= :name")
})
public class Category extends AbstractEntity {
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
