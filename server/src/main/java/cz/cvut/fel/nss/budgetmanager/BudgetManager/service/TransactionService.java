package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;


import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.WalletDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final WalletDao walletDao;

    @Autowired
    public TransactionService(TransactionDao transactionDao, WalletDao walletDao) {
        this.transactionDao = transactionDao;
        this.walletDao = walletDao;
    }

    @Transactional
    public List<Transaction> findAllTransactions(){
        return transactionDao.findAll();
    }

    @Transactional
    public Transaction findTransactionById(Long id) {
        Objects.requireNonNull(id);
        return transactionDao.find(id);
    }

    @Transactional
    public List<Transaction>  findTransactionByCategory(Category category){
        return transactionDao.findByCategory(category);
    }

    @Transactional
    public List<Transaction>  findTransactionsByAmount(BigDecimal amount){
        return transactionDao.findByAmount(amount);
    }

    @Transactional List<Transaction> findTransactionsByMessage(String message){
        return transactionDao.findByMessage(message);
    }

    @Transactional
    public void persist(Transaction transaction) {
        Objects.requireNonNull(transaction);
        transactionDao.persist(transaction);
    }

    @Transactional
    public void update(Transaction transaction) {
        Objects.requireNonNull(transaction);
        transactionDao.update(transaction);
    }

    @Transactional
    public void performTransaction(Transaction transaction) {
        Wallet wallet = transaction.getWallet();
        wallet.setAmount(wallet.getAmount().add(transaction.getMoney()));
        walletDao.update(wallet);

        // TODO not sure about checking what type we wanna use
        if (transaction.getMoney().compareTo(BigDecimal.ZERO) >= 0) {
            transaction.setTypeTransaction(TypeTransaction.INCOME);
        } else {
            transaction.setTypeTransaction(TypeTransaction.EXPANSE);
        }
        transactionDao.update(transaction);
    }

    @Transactional
    public void editTransaction(Transaction transaction) {
        Transaction existingTransaction = transactionDao.find(transaction.getTransID());
        if (existingTransaction == null) {
            throw new NotFoundException("Transaction not found");
        }

        existingTransaction.setName(transaction.getName());
        existingTransaction.setCategory(transaction.getCategory());
        existingTransaction.setMoney(transaction.getMoney());
        // Update other relevant fields as needed!!!

        transactionDao.update(existingTransaction);
    }

}
