package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "findCategoryByName", query = "SELECT c FROM Category c WHERE c.name = :name "),
        @NamedQuery(name = "updateCategoryByName", query = "UPDATE Category set name = name where Category.name= :name")
})
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long categoryId;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
