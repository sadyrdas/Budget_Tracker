package cz.cvut.fel.nss.budgetmanager.Dispatcher.dto;


import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletResponseDTO {
    private Long walletId;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal budgetLimit;
    private String clientEmail;
    private String name;
}

