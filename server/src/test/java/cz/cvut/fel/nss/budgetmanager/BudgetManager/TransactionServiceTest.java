package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Transaction;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.TransactionDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.WalletDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private WalletDao walletDao;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction();
        transaction.setTransId(1L);
        // Set additional fields for transaction as necessary
    }

    @Test
    public void testFindAllTransactions() {
        when(transactionDao.findAll()).thenReturn(Collections.singletonList(transaction));

        List<Transaction> transactions = transactionService.findAllTransactions();

        assertThat(transactions).isNotEmpty();
        assertThat(transactions).hasSize(1);
        assertThat(transactions.get(0).getTransId()).isEqualTo(transaction.getTransId());
    }

    @Test
    public void testFindTransactionById() {
        when(transactionDao.find(any(Long.class))).thenReturn(transaction);

        Transaction foundTransaction = transactionService.findTransactionById(transaction.getTransId());

        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction.getTransId()).isEqualTo(transaction.getTransId());
    }


    @Test
    public void testPersist() {
        doNothing().when(transactionDao).persist(any(Transaction.class));

        transactionService.persist(transaction);

        verify(transactionDao, times(1)).persist(eq(transaction));
    }

    @Test
    public void testUpdate() {
        when(transactionDao.update(any(Transaction.class))).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.update(transaction);

        assertThat(updatedTransaction).isNotNull();
        assertThat(updatedTransaction.getTransId()).isEqualTo(transaction.getTransId());
    }


    @Test
    public void testDeleteTransaction() {
        when(transactionDao.find(any(Long.class))).thenReturn(transaction);
        doNothing().when(transactionDao).remove(any(Transaction.class));

        transactionService.deleteTransaction(transaction.getTransId());

        verify(transactionDao, times(1)).remove(eq(transaction));
    }

}
