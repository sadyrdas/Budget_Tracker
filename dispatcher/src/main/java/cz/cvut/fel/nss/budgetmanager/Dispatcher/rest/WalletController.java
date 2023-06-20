package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.WalletGoalResponseDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.WalletResponseDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("rest/wallet")
public class WalletController {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    @Autowired
    public WalletController(RestTemplate restTemplate, @Value("${server1.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @PutMapping(value = "/money", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMoneyToWallet(@RequestBody BigDecimal amount){
        HttpEntity<BigDecimal> request = new HttpEntity<>(amount);
        return restTemplate.exchange(serverUrl + "/money",HttpMethod.PUT, request, Void.class);
    }

    @PostMapping(value = "/goal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletGoalResponseDTO> addGoal(@RequestBody WalletGoalResponseDTO walletGoalResponseDTO) {
        HttpEntity<WalletGoalResponseDTO> request = new HttpEntity<>(walletGoalResponseDTO);
        return restTemplate.exchange(serverUrl + "/goal", HttpMethod.POST, request, WalletGoalResponseDTO.class);
    }

    @PutMapping(value = "/currency")
    public ResponseEntity<Void> changeCurrency(@RequestParam("currency") Currency currency){
        HttpEntity<Currency> request = new HttpEntity<>(currency);
        return restTemplate.exchange(serverUrl + "/currency?currency={currency}", HttpMethod.PUT, request, Void.class, currency);
    }

    @GetMapping(value = "/myWallet")
    public ResponseEntity<WalletResponseDTO> getWallet(){
        return restTemplate.getForEntity(serverUrl + "/myWallet", WalletResponseDTO.class);
    }
}
