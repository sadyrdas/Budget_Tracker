package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.WalletDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
public class WalletService {

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;

    private Wallet wallet;

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
                    wallet.setAmount(initialAmount);
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


    public void deleteWallet(Wallet wallet) {
        Objects.requireNonNull(wallet);
        walletDao.remove(wallet);
    }

}
