package cz.cvut.fel.nss.budgetmanager.Dispatcher.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class User {

    private Long clientId;
    private String email;
    private String password;
    private String username;
    private Wallet wallet;

    public User(String email, String password, String username, Wallet wallet) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.wallet = wallet;
    }

    public User() {
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }
}
