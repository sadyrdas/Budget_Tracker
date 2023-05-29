package cz.cvut.fel.nss.budgetmanager.BudgetManager.dto;

import java.math.BigDecimal;
import java.util.Map;

public class WalletGoalResponseDTO {
    private Map<String, BigDecimal> goal;

    public Map<String, BigDecimal> getGoal() {
        return goal;
    }

    public void setGoal(Map<String, BigDecimal> goal) {
        this.goal = goal;
    }
}

