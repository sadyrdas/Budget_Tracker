package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.*;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.NotificationType;
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
    public WalletService(WalletDao walletDao, TransactionDao transactionDao, NotificationService notificationService) {
        this.walletDao = walletDao;
        this.transactionDao = transactionDao;
        this.wallet = null;
        this.notificationService = notificationService;
    }

    public void createSingletonWallet(BigDecimal initialAmount, String name, BigDecimal budgetLimit, User user) {
        if (wallet == null) {
            synchronized (this) {
                if (wallet == null) {
                    Wallet singletonWallet = new Wallet();
                    singletonWallet.setAmount(initialAmount);
                    singletonWallet.setName(name);
                    singletonWallet.setBudgetLimit(budgetLimit);
                    singletonWallet.setClient(user);
                    walletDao.persist(singletonWallet);
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

    public void addMoney(Wallet wallet, BigDecimal amount) {
        Objects.requireNonNull(wallet);
        wallet.setAmount(wallet.getAmount().add(amount));
        walletDao.update(wallet);
    }

    public Wallet getByClientEmail(String email) {
        return walletDao.findByClientEmail(email);
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
            notificationService.pushNotification(
                    wallet.getClient().getClientId(),
                    NotificationType.BUDGET_OVERLIMIT,
                    NotificationType.BUDGET_OVERLIMIT.getValue());
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
