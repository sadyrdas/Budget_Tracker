package cz.cvut.fel.nss.budgetmanager.Dispatcher.dto;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.TypeTransaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDTO {
    private String description;
    private BigDecimal money;
    private TypeTransaction typeTransaction;
    private String category;
    private String wallet;
    private LocalDateTime dateTime;
}
