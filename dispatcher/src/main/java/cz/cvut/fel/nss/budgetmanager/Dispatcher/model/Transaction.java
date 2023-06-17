package cz.cvut.fel.nss.budgetmanager.Dispatcher.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    private Long transId;
    private String description;
    private LocalDateTime date;
    private BigDecimal money;
    private TypeTransaction typeTransaction;
    private Wallet wallet;
    private Category category;
}