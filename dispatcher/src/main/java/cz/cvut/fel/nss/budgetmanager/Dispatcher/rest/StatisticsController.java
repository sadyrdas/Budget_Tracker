package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.TypeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/rest/statistics")
public class StatisticsController {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    /**
     * Creates a StatisticsController with a RestTemplate and server URL.
     *
     * @param restTemplate The RestTemplate for making HTTP requests.
     * @param serverUrl     The URL of the server.
     */
    @Autowired
    public StatisticsController(RestTemplate restTemplate, @Value("${server2.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    /**
     * Generates statistics based on the specified interval type.
     *
     * @param intervalType The TypeInterval specifying the interval type.
     * @return The generated statistics as a Map.
     * @throws RuntimeException if failed to generate statistics.
     */
    @GetMapping("/generate")
    public Map generateStatistics(@RequestParam("intervalType") TypeInterval intervalType) {
        String url = serverUrl + "?intervalType=" + intervalType;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate statistics");
        }
    }
}