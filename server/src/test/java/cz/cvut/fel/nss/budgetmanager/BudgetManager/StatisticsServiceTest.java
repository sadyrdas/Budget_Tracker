package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeInterval;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    @Mock
    private TransactionDao transactionDao;

    @InjectMocks
    private StatisticsService statisticsService;

    private Transaction transactionIncome;
    private Transaction transactionExpense;

    @BeforeEach
    public void setup() {
        transactionIncome = new Transaction();
        transactionIncome.setTransId(1L);
        transactionIncome.setTypeTransaction(TypeTransaction.INCOME);
        transactionIncome.setMoney(BigDecimal.valueOf(5000));

        transactionExpense = new Transaction();
        transactionExpense.setTransId(2L);
        transactionExpense.setTypeTransaction(TypeTransaction.EXPENSE);
        transactionExpense.setMoney(BigDecimal.valueOf(1000));
        // Set additional fields for transaction as necessary
    }

    @Test
    public void testGenerateStatistics() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionIncome);
        transactions.add(transactionExpense);
        when(transactionDao.getTransactionsWithinInterval(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions);

        Map<String, BigDecimal> statistics = statisticsService.generateStatistics(TypeInterval.MONTHLY);

        Map<String, BigDecimal> expectedStatistics = new HashMap<>();
        expectedStatistics.put("Total Income", BigDecimal.valueOf(5000));
        expectedStatistics.put("Total Expenses", BigDecimal.valueOf(1000));
        expectedStatistics.put("Net Income", BigDecimal.valueOf(4000));

        assertThat(statistics).isNotEmpty();
        assertThat(statistics).isEqualTo(expectedStatistics);
    }
}
