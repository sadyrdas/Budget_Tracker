package cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.AbstractEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Message extends AbstractEntity {

    private String info;
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
}
