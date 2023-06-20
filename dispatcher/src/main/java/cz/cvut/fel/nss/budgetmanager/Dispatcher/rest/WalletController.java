package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.WalletGoalResponseDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.dto.WalletResponseDTO;
import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("rest/wallet")
public class WalletController {
    @Autowired
    RestTemplate restTemplate;

    private String server1Url = "http://server:8081/rest/wallet";

    @PutMapping(value = "/money", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMoneyToWallet(@RequestBody BigDecimal amount){
        HttpEntity<BigDecimal> request = new HttpEntity<>(amount);
        ResponseEntity<Void> response = restTemplate.exchange(server1Url + "/money",HttpMethod.PUT, request, Void.class);
        return response;
    }

    @PostMapping(value = "/goal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletGoalResponseDTO> addGoal(@RequestBody WalletGoalResponseDTO walletGoalResponseDTO) {
        HttpEntity<WalletGoalResponseDTO> request = new HttpEntity<>(walletGoalResponseDTO);
        ResponseEntity<WalletGoalResponseDTO> response = restTemplate.exchange(server1Url + "/goal", HttpMethod.POST, request, WalletGoalResponseDTO.class);
        System.out.println(response);
        return response;
    }

    @PutMapping(value = "/currency")
    public ResponseEntity<Void> changeCurrency(@RequestParam("currency") Currency currency){
        HttpEntity<Currency> request = new HttpEntity<>(currency);
        ResponseEntity<Void> response = restTemplate.exchange(server1Url + "/currency?currency={currency}", HttpMethod.PUT, request, Void.class, currency);
        System.out.println(response);
        return response;
    }

    @GetMapping(value = "/myWallet")
    public ResponseEntity<WalletResponseDTO> getWallet(){
        ResponseEntity<WalletResponseDTO> response = restTemplate.getForEntity(server1Url + "/myWallet", WalletResponseDTO.class);
        System.out.println(response);
        return response;
    }
}
