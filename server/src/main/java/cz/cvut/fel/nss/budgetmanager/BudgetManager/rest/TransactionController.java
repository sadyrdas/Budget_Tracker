package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rest/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        return transaction;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        transactionService.persist(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        transaction.setDescription(updatedTransaction.getDescription());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setMoney(updatedTransaction.getMoney());
        transaction.setTypeTransaction(updatedTransaction.getTypeTransaction());
        transaction.setDate(updatedTransaction.getDate());
        // Update other relevant fields as needed

        transactionService.update(transaction);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        transactionService.deleteTransaction(transaction.getTransId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Transaction> searchTransaction(@RequestParam(required = false) Category category,
                                               @RequestParam(required = false) LocalDate date,
                                               @RequestParam(required = false) String description,
                                               @RequestParam(required = false) BigDecimal amount){
        return transactionService.searchTransactions(category, date, description, amount);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportTransaction() {
        Resource fileResource = transactionService.exportTransactions();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"transactions.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileResource);
    }
}
