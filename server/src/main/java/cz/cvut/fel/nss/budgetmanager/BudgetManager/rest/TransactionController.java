package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.TransactionResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Category;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
//import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionRepository;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionRepository;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.SecurityUtils;
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


import java.io.IOException;
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
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionService transactionService, WalletService walletService,
                                 CategoryService categoryService, TransactionRepository transactionRepository){
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.categoryService = categoryService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseDTO> getAllTransactions() {
        List<Transaction> list = transactionService.findAllTransactions();
        ModelMapper modelMapper = new ModelMapper();
        return list.stream().map(transaction -> modelMapper.map(transaction, TransactionResponseDTO.class)).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transaction, TransactionResponseDTO.class);


        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody Transaction transaction) {
        Wallet wallet = SecurityUtils.getCurrentUser().getWallet();
        Category category = categoryService.getCategoryByName(transaction.getCategory().getName());
        ModelMapper modelMapper = new ModelMapper();
        Transaction.Builder builder = new Transaction.Builder();
        builder
                .typeTransaction(transaction.getTypeTransaction())
                .category(category)
                .transDate()
                .wallet(wallet)
                .money(transaction.getMoney())
                .name(transaction.getDescription());
        transactionService.persist(builder.build());
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(builder.build(), TransactionResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponseDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
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

        ModelMapper modelMapper = new ModelMapper();
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transactionService.update(transaction),
                TransactionResponseDTO.class);
        return ResponseEntity.ok(transactionResponseDTO);
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
        try {
            Resource fileResource = transactionService.exportTransactionsToTxtFile();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"transactions.txt\"")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(fileResource);
        } catch (IOException e) {
            // Handle the exception accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping(value = "/findByDescription/{description}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Transaction> findByDescription(@PathVariable String description) {
//        return transactionRepository.findTransactionByDescription(description);
//    }
}
