package cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification;

import jakarta.persistence.*;

@Entity
public class NotificationSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
}
