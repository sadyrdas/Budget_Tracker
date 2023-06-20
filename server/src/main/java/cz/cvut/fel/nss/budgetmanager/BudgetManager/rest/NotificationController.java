package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.NotificationType;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing notifications.
 */
@RestController
@RequestMapping("/rest/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Constructs a new NotificationController with the provided NotificationService.
     *
     * @param notificationService The NotificationService to be used.
     */
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Pushes a notification for a user.
     *
     * @param userId The ID of the user.
     * @param type   The type of the notification.
     */
    @PostMapping
    public void pushNotification(@RequestParam String userId, @RequestParam NotificationType type) {
        notificationService.pushNotification(Long.parseLong(userId), type, "Cho chochu pishu, chel!");
    }
}
