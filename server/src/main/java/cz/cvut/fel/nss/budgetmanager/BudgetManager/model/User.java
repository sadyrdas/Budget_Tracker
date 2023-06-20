package cz.cvut.fel.nss.budgetmanager.BudgetManager.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a user entity in the system.
 */
@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.deleteByEmail", query = "DELETE  FROM User u WHERE u.email = :email")
})
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Long clientId;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @OneToOne(mappedBy = "client")
    private Wallet wallet;

    /**
     * Constructs a new User object with the specified email, password, username, and wallet.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @param username The username of the user.
     * @param wallet   The wallet associated with the user.
     */
    public User(String email, String password, String username, Wallet wallet) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.wallet = wallet;
    }

    public User() {
    }

    /**
     * Encodes the password using the provided password encoder.
     *
     * @param encoder The password encoder.
     */
    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public Long getClientId() {
        return clientId;
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

    public String getUsername() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
