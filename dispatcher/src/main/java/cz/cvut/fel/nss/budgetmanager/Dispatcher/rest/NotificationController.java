package cz.cvut.fel.nss.budgetmanager.Dispatcher.rest;

import cz.cvut.fel.nss.budgetmanager.Dispatcher.model.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for managing notifications.
 */
@RestController
@RequestMapping("/rest/notifications")
public class NotificationController {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    @Autowired
    public NotificationController(RestTemplate restTemplate, @Value("${server2.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    /**
     * Pushes a notification for a user.
     *
     * @param userId The ID of the user.
     * @param type   The type of the notification.
     */
    @PostMapping
    public void pushNotification(@RequestParam String userId, @RequestParam NotificationType type) {
        // Create URI with query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl)
                .queryParam("userId", userId)
                .queryParam("type", type);

        // Create HttpEntity object with headers if needed, otherwise pass null
        HttpEntity<?> entity = null;

        // Send POST request
        restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Void.class);    }
}
