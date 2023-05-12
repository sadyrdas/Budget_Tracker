package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import org.hibernate.query.sqm.IntervalType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final TransactionDao transactionDao;

    public StatisticsService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

//    public Map<String, BigDecimal> generateStatistics(IntervalType intervalType) {
//        LocalDate startDate = calculateStartDate(intervalType);
//        LocalDate endDate = LocalDate.now();
//
//        List<Transaction> transactions = transactionDao.getTransactionsWithinInterval(startDate, endDate);
//
//        Map<String, BigDecimal> statistics = new HashMap<>();
//
//        // Perform necessary calculations based on transactions
//        BigDecimal totalIncome = calculateTotalAmount(transactions, TypeTransaction.INCOME);
//        BigDecimal totalExpenses = calculateTotalAmount(transactions, TypeTransaction.EXPANSE);
//
//        statistics.put("Total Income", totalIncome);
//        statistics.put("Total Expenses", totalExpenses);
//        statistics.put("Net Income", totalIncome.subtract(totalExpenses));
//
//        return statistics;
//    }

//    private LocalDate calculateStartDate(IntervalType intervalType) {
//        LocalDate startDate;
//
//        switch (intervalType) {
//            case WEEKLY:
//                startDate = LocalDate.now().minusWeeks(1);
//                break;
//            case MONTHLY:
//                startDate = LocalDate.now().minusMonths(1);
//                break;
//            case YEARLY:
//                startDate = LocalDate.now().minusYears(1);
//                break;
//            case CUSTOM:
//            default:
//                // Define your custom logic to determine the start date for the interval
//                startDate = LocalDate.now();
//                break;
//        }
//
//        return startDate;
//    }

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
