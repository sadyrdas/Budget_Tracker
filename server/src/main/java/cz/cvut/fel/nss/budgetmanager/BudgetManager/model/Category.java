package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a category entity in the system.
 */
@Entity
@Table(name = "category")
@NamedQueries({@NamedQuery(name = "findCategoryByName",
        query = "SELECT c FROM Category c WHERE c.name = :name "),
        @NamedQuery(name = "updateCategoryByName", query = "UPDATE Category c set c.name =: name where c.name=: name")})
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long categoryId;

    @Basic(optional = false)
    @Column(nullable = false, name = "name")
    private String name;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
