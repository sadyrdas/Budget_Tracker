package cz.cvut.fel.nss.budgetmanager.Dispatcher.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class WalletGoalResponseDTO {
    private Map<String, BigDecimal> goal;
}

