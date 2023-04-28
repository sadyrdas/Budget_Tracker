package cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.AbstractEntity;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class Notification extends AbstractEntity {
    private LocalDateTime dateTime;

    @OneToOne
    private Message info;
    private String type;


    @ManyToOne
    private User userId;

    public Notification(LocalDateTime dateTime, Message info, String type, User userId) {
        this.dateTime = dateTime;
        this.info = info;
        this.type = type;
        this.userId = userId;
    }

    public Notification() {

    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Message getInfo() {
        return info;
    }

    public void setInfo(Message info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
