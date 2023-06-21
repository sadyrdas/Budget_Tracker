package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeInterval;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StatisticsService {

    private final TransactionDao transactionDao;

    /**
     * Constructs a new StatisticsService with the provided TransactionDao.
     *
     * @param transactionDao The TransactionDao implementation used for data access.
     */
    public StatisticsService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    /**
     * Generates statistics based on the specified interval type.
     *
     * @param intervalType The interval type for which to generate statistics.
     * @return A map containing the generated statistics.
     */
    public Map<String, BigDecimal> generateStatistics(TypeInterval intervalType) {
        LocalDateTime startDate = calculateStartDate(intervalType);
        LocalDateTime endDate = LocalDateTime.now();

        List<Transaction> transactions = transactionDao.getTransactionsWithinInterval(startDate, endDate);

        Map<String, BigDecimal> statistics = new HashMap<>();

        // Perform necessary calculations based on transactions
        BigDecimal totalIncome = calculateTotalAmount(transactions, TypeTransaction.INCOME);
        BigDecimal totalExpenses = calculateTotalAmount(transactions, TypeTransaction.EXPENSE);

        statistics.put("Total Income", totalIncome);
        statistics.put("Total Expenses", totalExpenses);
        statistics.put("Net Income", totalIncome.subtract(totalExpenses));

        return statistics;
    }

    /**
     * Calculates the start date for the specified interval type.
     *
     * @param intervalType The interval type.
     * @return The start date for the interval.
     */
    private LocalDateTime calculateStartDate(TypeInterval intervalType) {
        LocalDateTime startDate;

        switch (intervalType) {
            case WEEKLY:
                startDate = LocalDateTime.now().minusWeeks(1);
                break;
            case MONTHLY:
                startDate = LocalDateTime.now().minusMonths(1);
                break;
            case YEARLY:
                startDate = LocalDateTime.now().minusYears(1);
                break;
            default:
                // Define your custom logic to determine the start date for the interval
                startDate = LocalDateTime.now();
                break;
        }

        return startDate;
    }

    /**
     * Calculates the total amount for transactions of the specified transaction type.
     *
     * @param transactions     The list of transactions.
     * @param transactionType  The transaction type.
     * @return The total amount for transactions of the specified type.
     */
    private BigDecimal calculateTotalAmount(List<Transaction> transactions, TypeTransaction transactionType) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getTypeTransaction() == transactionType) {
                totalAmount = totalAmount.add(transaction.getMoney());
            }
        }

        return totalAmount;
    }
}
