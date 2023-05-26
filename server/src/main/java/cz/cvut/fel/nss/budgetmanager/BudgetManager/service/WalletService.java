package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.WalletDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class WalletService {

    private TransactionService transactionService;
    private NotificationService notificationService;

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;
    private Wallet wallet;

    @Autowired
    public WalletService(WalletDao walletDao, TransactionDao transactionDao) {
        this.walletDao = walletDao;
        this.transactionDao = transactionDao;
        this.wallet = null;
    }

    public void createSingletonWallet(BigDecimal initialAmount) {
        if (wallet == null) {
            synchronized (this) {
                if (wallet == null) {
                    Wallet singletonWallet = new Wallet();
                    singletonWallet.setAmount(initialAmount);
                    walletDao.persist(wallet);
                    wallet = singletonWallet;
                }
            }
        }
    }

    public Wallet getSingletonWallet() {
        if (wallet == null) {
            synchronized (this) {
                if (wallet == null) {
                    wallet = walletDao.findSingletonWallet();
                }
            }
        }
        return wallet;
    }

    public void updateWallet(Wallet wallet) {
        Objects.requireNonNull(wallet);
        walletDao.update(wallet);
    }

    public BigDecimal getTotalBalance() {
        if (wallet != null) {
            return wallet.getAmount();
        }
        return BigDecimal.ZERO;
    }

    public Wallet getWalletById(Long walletId) {
        return walletDao.find(walletId);
    }

    public BigDecimal calculateTotalIncome(Wallet wallet) {
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Transaction transaction : wallet.getTransactions()) {
            if (transaction.getTypeTransaction() == TypeTransaction.INCOME) {
                totalIncome = totalIncome.add(transaction.getMoney());
            }
        }

        return totalIncome;
    }

    public List<Transaction> getTransactions(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        return wallet.getTransactions();
    }

    public List<Transaction> getTransactions() {
        Wallet wallet = getSingletonWallet();
        return wallet.getTransactions();
    }

    public Map<String, BigDecimal> calculateBudgetProgress(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        BigDecimal totalIncome = transactionService.calculateTotalIncome(wallet);
        BigDecimal totalExpenses = transactionService.calculateTotalExpenses(wallet);
        BigDecimal balance = totalIncome.subtract(totalExpenses);

        Map<String, BigDecimal> budgetProgress = new HashMap<>();
        budgetProgress.put("totalIncome", totalIncome);
        budgetProgress.put("totalExpenses", totalExpenses);
        budgetProgress.put("balance", balance);

        return budgetProgress;
    }

    public void checkBudgetLimit(Long walletId) {
        Wallet wallet = getSingletonWallet();
        BigDecimal totalExpenses = transactionService.calculateTotalExpenses(wallet);

        if (totalExpenses.compareTo(wallet.getBudgetLimit()) > 0) {
            notificationService.sendBudgetOverlimitNotification(wallet);
        }
    }

    public void addGoal(String goal, BigDecimal money){
        Map<String, BigDecimal> currentBudgetGoals = wallet.getBudgetGoal();
        if (currentBudgetGoals == null) {
            currentBudgetGoals = new HashMap<>();
        }
        currentBudgetGoals.put(goal, money);
        wallet.setBudgetGoal(currentBudgetGoals);
    }
}
