package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.TransactionResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.CategoryService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.TransactionService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("rest/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final WalletService walletService;
    private final CategoryService categoryService;

    @Autowired
    public TransactionController(TransactionService transactionService, WalletService walletService, CategoryService categoryService){
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseDTO> getAllTransactions() {
        List<Transaction> list = transactionService.findAllTransactions();
        ModelMapper modelMapper = new ModelMapper();
        return list.stream().map(transaction -> modelMapper.map(transaction, TransactionResponseDTO.class)).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transaction, TransactionResponseDTO.class);


        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDTO> createTransaction(@PathVariable Long id,  @RequestBody Transaction transaction) {
        Wallet wallet = walletService.getWalletById(id);
        transaction.setWallet(wallet);
        Category category = categoryService.getCategoryByName(transaction.getCategory().getName());
        transaction.setCategory(category);
        transaction.setDate(LocalDateTime.now());
        ModelMapper modelMapper = new ModelMapper();
        transactionService.persist(transaction);
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transaction, TransactionResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponseDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        transaction.setDescription(updatedTransaction.getDescription());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setMoney(updatedTransaction.getMoney());
        transaction.setTypeTransaction(updatedTransaction.getTypeTransaction());
        transaction.setDate(updatedTransaction.getDate());

        transactionService.update(transaction);
        ModelMapper modelMapper = new ModelMapper();
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transaction, TransactionResponseDTO.class);
        return ResponseEntity.ok(transactionResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        transactionService.deleteTransaction(transaction.getTransId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<TransactionResponseDTO> searchTransaction(@RequestParam(required = false) Category category,
                                               @RequestParam(required = false) LocalDate date,
                                               @RequestParam(required = false) String description,
                                               @RequestParam(required = false) BigDecimal amount){
        List<Transaction> list = transactionService.searchTransactions(category, date, description, amount);
        ModelMapper modelMapper = new ModelMapper();
        return list.stream().map(transaction -> modelMapper.map(transaction, TransactionResponseDTO.class)).toList();
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
