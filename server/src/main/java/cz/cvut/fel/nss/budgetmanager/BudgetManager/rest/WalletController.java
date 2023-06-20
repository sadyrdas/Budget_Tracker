package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.WalletGoalResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.WalletResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Currency;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.security.SecurityUtils;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.UserService;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * REST controller for managing wallet-related operations.
 */
@RestController
@RequestMapping("rest/wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;

    /**
     * Constructs a new WalletController with the provided WalletService and UserService.
     *
     * @param walletService The WalletService to be used.
     * @param userService  The UserService to be used.
     */
    @Autowired
    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    /**
     * Updates a wallet.
     *
     * @param updatedWallet The updated wallet object.
     * @return The ResponseEntity containing the updated WalletResponseDTO object and the appropriate status.
     */
    @PutMapping(value = "/wallet",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponseDTO> updateWallet(@RequestBody Wallet updatedWallet){
        Wallet wallet = SecurityUtils.getCurrentUser().getWallet();

        wallet.setAmount(updatedWallet.getAmount());
        wallet.setBudgetLimit(updatedWallet.getBudgetLimit());
        wallet.setCurrency(updatedWallet.getCurrency());
        walletService.updateWallet(wallet);

        ModelMapper modelMapper = new ModelMapper();
        WalletResponseDTO walletResponseDTO = modelMapper.map(wallet, WalletResponseDTO.class);
        return ResponseEntity.ok(walletResponseDTO);
    }

    /**
     * Adds money to a wallet.
     *
     * @param amount The amount of money to add.
     * @return The ResponseEntity with no content and the appropriate status.
     */
    @PutMapping(value = "/addMoney")
    public ResponseEntity<Void> addMoneyToWallet(@RequestParam("amount") BigDecimal amount) {
        Wallet userWallet = SecurityUtils.getCurrentUser().getWallet();
        walletService.addMoney(userWallet, amount);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Adds a goal to a wallet.
     *
     * @param request The WalletGoalResponseDTO object containing the goal details.
     * @return The ResponseEntity containing the updated WalletGoalResponseDTO object and the appropriate status.
     * @throws NotFoundException if the goal is null.
     */
    @PostMapping(value = "/addGoal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<WalletGoalResponseDTO> addGoal(@RequestBody WalletGoalResponseDTO request) {
        Wallet wallet = SecurityUtils.getCurrentUser().getWallet();
        Map<String, BigDecimal> goal = request.getGoal();
        if (goal != null && !goal.isEmpty()) {
            Map.Entry<String, BigDecimal> entry = goal.entrySet().iterator().next();
            String goalKey = entry.getKey();
            BigDecimal moneyForGoal = entry.getValue();
            walletService.addGoal(goalKey, moneyForGoal, wallet.getWalletId());
        }
        if (goal == null){
            throw new NotFoundException("Goal must not be null!");
        }
        walletService.updateWallet(wallet);

        ModelMapper modelMapper = new ModelMapper();
        WalletGoalResponseDTO walletGoalResponseDTO = modelMapper.map(wallet, WalletGoalResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(walletGoalResponseDTO);
    }

    /**
     * Changes the currency of a wallet.
     *
     * @param currency The new currency.
     * @return The ResponseEntity with no content and the appropriate status.
     */
    @PutMapping(value = "/currency")
    public ResponseEntity<Void> changeCurrency(@RequestParam("currency")Currency currency){
        Wallet userWallet = SecurityUtils.getCurrentUser().getWallet();
        walletService.changeCurrency(currency, userWallet);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves the wallet of the current user.
     *
     * @return The ResponseEntity containing the WalletResponseDTO object and the appropriate status.
     */
    @GetMapping(value = "/myWallet")
    public ResponseEntity<WalletResponseDTO> getWallet() {
        Wallet wallet = SecurityUtils.getCurrentUser().getWallet();
        ModelMapper modelMapper = new ModelMapper();
        WalletResponseDTO walletResponseDTO = modelMapper.map(wallet, WalletResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(walletResponseDTO);
    }

}
