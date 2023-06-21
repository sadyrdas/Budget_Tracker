package cz.cvut.fel.nss.budgetmanager.Dispatcher.model;

import lombok.Getter;

@Getter
public enum TypeTransaction {
    INCOME("INCOME"),
    EXPENSE("EXPENSE");
    private String type;

    TypeTransaction(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
