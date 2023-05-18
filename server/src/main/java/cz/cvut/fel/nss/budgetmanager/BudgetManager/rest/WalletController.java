package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("rest/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> updateWallet(@RequestBody Wallet updatedWallet){
        Wallet wallet = walletService.getSingletonWallet();

        wallet.setName(updatedWallet.getName());
        wallet.setAmount(updatedWallet.getAmount());
        wallet.setBudgetLimit(updatedWallet.getBudgetLimit());
        wallet.setCurrency(updatedWallet.getCurrency());
        wallet.setTransactions(updatedWallet.getTransactions());

        walletService.updateWallet(wallet);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> createWallet(@RequestParam("amount") BigDecimal inicialAmount){
        walletService.createSingletonWallet(inicialAmount);
        Wallet singletonWallet = walletService.getSingletonWallet();
        return ResponseEntity.status(HttpStatus.CREATED).body(singletonWallet);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> addGoal(@RequestParam("goal") String goal, @RequestParam("money_goal") BigDecimal moneyForGoal){
        Wallet wallet = walletService.getSingletonWallet();
        walletService.addGoal(goal, moneyForGoal);
        walletService.updateWallet(wallet);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(wallet);
    }
}
