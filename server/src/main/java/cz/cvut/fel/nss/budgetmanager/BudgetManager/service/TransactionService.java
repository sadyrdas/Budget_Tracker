package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;


import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeTransaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.WalletDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final WalletDao walletDao;

    /**
     * Constructs a new TransactionService with the provided TransactionDao and WalletDao.
     *
     * @param transactionDao The TransactionDao implementation used for data access.
     * @param walletDao      The WalletDao implementation used for data access.
     */
    @Autowired
    public TransactionService(TransactionDao transactionDao, WalletDao walletDao) {
        this.transactionDao = transactionDao;
        this.walletDao = walletDao;
    }

    /**
     * Retrieves all transactions.
     *
     * @return A list of all transactions.
     */
    @Transactional
    public List<Transaction> findAllTransactions(){
        return transactionDao.findAll();
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction.
     * @return The transaction with the specified ID, or null if not found.
     */
    @Transactional
    @Cacheable(value = "trans", key = "#id")
    public Transaction findTransactionById(Long id) {
        Objects.requireNonNull(id);
        log.info("Fetching the transaction {} from DB", id);
        return transactionDao.find(id);
    }

    /**
     * Retrieves transactions by category.
     *
     * @param category The category of the transactions.
     * @return A list of transactions with the specified category.
     */
    @Transactional
    public List<Transaction>  findTransactionByCategory(Category category){
        return transactionDao.findByCategory(category);
    }

    /**
     * Retrieves transactions by amount.
     *
     * @param money The amount of the transactions.
     * @return A list of transactions with the specified amount.
     */
    @Transactional
    public List<Transaction>  findTransactionsByAmount(BigDecimal money){
        return transactionDao.findByAmount(money);
    }

    /**
     * Retrieves transactions by description.
     *
     * @param description The description of the transactions.
     * @return A list of transactions with the specified description.
     */
    @Transactional
    public List<Transaction> findTransactionsByMessage(String description){
        return transactionDao.findByDescription(description);
    }

    /**
     * Searches for transactions based on the provided criteria.
     *
     * @param category    The category of the transactions (optional).
     * @param date        The date of the transactions (optional).
     * @param description The description of the transactions (optional).
     * @param amount      The amount of the transactions (optional).
     * @return A list of transactions that match the specified criteria.
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchTransactions(Category category, LocalDate date, String description, BigDecimal amount) {
        // Perform the search based on the provided criteria
        if (category != null) {
            // Search by category
            return transactionDao.findByCategory(category);
        } else if (date != null) {
            // Search by date
            // Adjust the date parameter based on your requirements (e.g., include a date range)
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.atTime(LocalTime.MAX);
            return transactionDao.getTransactionsWithinInterval(startDate, endDate);
        } else if (description != null) {
            // Search by description
            return transactionDao.findByDescription(description);
        } else if (amount != null) {
            // Search by amount
            return transactionDao.findByAmount(amount);
        } else {
            // No criteria provided, return all transactions
            return transactionDao.findAll();
        }
    }

    /**
     * Persists a new transaction.
     *
     * @param transaction The transaction to persist.
     */
    @Transactional
    public void persist(Transaction transaction) {
        Objects.requireNonNull(transaction);
        transactionDao.persist(transaction);
    }

    /**
     * Updates a transaction.
     *
     * @param transaction The transaction to update.
     * @return The updated transaction.
     */
    @Transactional
    @CachePut(value = "trans", key = "#transaction.getTransId()")
    public Transaction update(Transaction transaction) {
        Objects.requireNonNull(transaction);
        log.info("Updated the transaction with id {}", transaction.getTransId());
        transactionDao.update(transaction);
        return transaction;
    }

    /**
     * Performs a transaction, updating the wallet's amount and type.
     *
     * @param transaction The transaction to perform.
     */
    @Transactional
    public void performTransaction(Transaction transaction) {
        Wallet wallet = transaction.getWallet();
        wallet.setAmount(wallet.getAmount().add(transaction.getMoney()));
        walletDao.update(wallet);

        if (transaction.getMoney().compareTo(BigDecimal.ZERO) >= 0) {
            transaction.setTypeTransaction(TypeTransaction.INCOME);
        } else {
            transaction.setTypeTransaction(TypeTransaction.EXPENSE);
        }
        transactionDao.update(transaction);
    }

    /**
     * Edits a transaction by updating its fields.
     *
     * @param transaction The updated transaction.
     */
    @Transactional
    public void editTransaction(Transaction transaction) {
        Transaction existingTransaction = transactionDao.find(transaction.getTransId());
        if (existingTransaction == null) {
            throw new NotFoundException("Transaction not found");
        }

        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setCategory(transaction.getCategory());
        existingTransaction.setMoney(transaction.getMoney());
        existingTransaction.setTypeTransaction(transaction.getTypeTransaction());
        existingTransaction.setDate(transaction.getDate());
        // Update other relevant fields as needed!!!

        transactionDao.update(existingTransaction);
//        performTransaction(existingTransaction); //проверка на перерасчет после изменения транзакции
    }

    /**
     * Calculates the total expenses for a wallet based on its transactions.
     *
     * @param wallet The wallet.
     * @return The total expenses for the wallet.
     */
    @Transactional
    public BigDecimal calculateTotalExpenses(Wallet wallet) {
        List<Transaction> transactions = wallet.getTransactions();
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getTypeTransaction() == TypeTransaction.EXPENSE) {
                totalExpenses = totalExpenses.add(transaction.getMoney());
            }
        }
        return totalExpenses;
    }

    /**
     * Calculates the total income for a wallet based on its transactions.
     *
     * @param wallet The wallet.
     * @return The total income for the wallet.
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalIncome(Wallet wallet) {
        BigDecimal totalIncome = BigDecimal.ZERO;

        // Iterate through the transactions in the wallet and calculate the total income
        for (Transaction transaction : wallet.getTransactions()) {
            if (transaction.getTypeTransaction() == TypeTransaction.INCOME) {
                totalIncome = totalIncome.add(transaction.getMoney());
            }
        }

        return totalIncome;
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param id The ID of the transaction to delete.
     */
    @Transactional
    @CacheEvict(value = "trans", key = "#id")
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionDao.find(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }

        transactionDao.remove(transaction);
    }

    /**
     * Exports transactions to a text file.
     *
     * @return A Resource representing the exported text file.
     * @throws IOException If an I/O error occurs while exporting the transactions.
     */
    public Resource exportTransactionsToTxtFile() throws IOException {
        String filePath = "server/src/main/resources/transactions.txt";
        List<Transaction> transactions = transactionDao.findAll();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Transaction transaction : transactions) {
                writer.write("Date: " + formatTextField(transaction.getDate().toString()));
                writer.newLine();
                writer.write("Description: " + formatTextField(transaction.getDescription()));
                writer.newLine();
                writer.write("Category: " + formatTextField(transaction.getCategory().getName()));
                writer.newLine();
                writer.write("Amount: " + formatTextField(transaction.getMoney().toString()));
                writer.newLine();
                writer.newLine();
            }
        }

        // Create a FileSystemResource from the generated text file
        return new FileSystemResource(filePath);
    }

    /**
     * Formats a text field for the exported transactions file.
     *
     * @param field The text field to format.
     * @return The formatted text field.
     */
    private String formatTextField(String field) {
        // Handle null values
        if (field == null) {
            return "";
        }
        // Add any additional formatting specific to the text file format
        // For example, you might want to replace special characters or adjust the formatting rules.
        return field;
    }

}
