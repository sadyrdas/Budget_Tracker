package cz.cvut.fel.nss.budgetmanager.BudgetManager.security.token;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auth_tokens")
@NamedQueries(
        @NamedQuery(name = "findActiveUserToken", query = "SELECT a FROM AuthToken a WHERE a.userId = :userId")
)
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

}
