package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.UserDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.WalletGoalResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.dto.WalletResponseDTO;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.exceptions.NotFoundException;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Currency;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.User;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.Wallet;
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

@RestController
@RequestMapping("rest/wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;

    @Autowired
    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;

    }

    @PutMapping(value = "/wallet",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponseDTO> updateWallet(@RequestBody Wallet updatedWallet){
        Wallet wallet = walletService.getSingletonWallet();

        wallet.setName(updatedWallet.getName());
        wallet.setAmount(updatedWallet.getAmount());
        wallet.setBudgetLimit(updatedWallet.getBudgetLimit());
        wallet.setCurrency(updatedWallet.getCurrency());
        walletService.updateWallet(wallet);

        ModelMapper modelMapper = new ModelMapper();
        WalletResponseDTO walletResponseDTO = modelMapper.map(wallet, WalletResponseDTO.class);
        return ResponseEntity.ok(walletResponseDTO);
    }

    @PostMapping("/createWallet")
    public ResponseEntity<WalletResponseDTO> createWallet(@RequestBody Wallet wallet) {
        User user = userService.findUserByEmail(wallet.getClient().getEmail());
        walletService.createSingletonWallet(wallet.getAmount(), wallet.getName(), wallet.getBudgetLimit(), user, wallet.getCurrency());
        Wallet singletonWallet = walletService.getSingletonWallet();
        UserDTO clientDTO = new UserDTO();
        clientDTO.setEmail(wallet.getClient().getEmail());

        ModelMapper modelMapper = new ModelMapper();
        WalletResponseDTO walletResponseDTO = modelMapper.map(singletonWallet, WalletResponseDTO.class);
        walletResponseDTO.setClientEmail(clientDTO.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(walletResponseDTO);
    }

    @PutMapping(value = "/addMoney")
    public ResponseEntity<Void> addMoneyToWallet(@RequestParam("user") String email, @RequestParam("amount") BigDecimal amount) {
        Wallet userWallet = walletService.getByClientEmail(email);
        walletService.addMoney(userWallet, amount);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/addGoal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<WalletGoalResponseDTO> addGoal(@RequestBody WalletGoalResponseDTO request) {
        Wallet wallet = walletService.getSingletonWallet();
        Map<String, BigDecimal> goal = request.getGoal();
        if (goal != null && !goal.isEmpty()) {
            Map.Entry<String, BigDecimal> entry = goal.entrySet().iterator().next();
            String goalKey = entry.getKey();
            BigDecimal moneyForGoal = entry.getValue();
            walletService.addGoal(goalKey, moneyForGoal);
        }
        if (goal == null){
            throw new NotFoundException("Goal must not be null!");
        }
        walletService.updateWallet(wallet);

        ModelMapper modelMapper = new ModelMapper();
        WalletGoalResponseDTO walletGoalResponseDTO = modelMapper.map(wallet, WalletGoalResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(walletGoalResponseDTO);
    }

    @PutMapping(value = "/currency")
    public ResponseEntity<Void> changeCurrency(@RequestParam("currency")Currency currency, @RequestParam("user") String email){
        Wallet userWallet = walletService.getByClientEmail(email);
        walletService.changeCurrency(currency, userWallet);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/yourWallet")
    public ResponseEntity<WalletResponseDTO> getWallet(@RequestParam("id") Long id){
        Wallet wallet = walletService.getWalletById(id);
        ModelMapper modelMapper = new ModelMapper();
        WalletResponseDTO walletResponseDTO = modelMapper.map(wallet, WalletResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(walletResponseDTO);
    }

}
