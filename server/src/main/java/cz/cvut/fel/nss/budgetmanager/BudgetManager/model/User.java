package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;


import jakarta.persistence.*;

@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity {
    private String email;
    private String password;
    private String username;


    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }


    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
