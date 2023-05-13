package cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String info;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Message(String info, LocalDateTime dateTime) {
        this.info = info;
        this.dateTime = dateTime;
    }

    public Message() {

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }
}
