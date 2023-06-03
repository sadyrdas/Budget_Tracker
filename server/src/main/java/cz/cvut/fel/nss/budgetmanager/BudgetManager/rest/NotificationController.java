package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.NotificationType;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public void pushNotification(@RequestParam String userId, @RequestParam NotificationType type) {
        notificationService.pushNotification(Long.parseLong(userId), type, "Keeek!");
    }

    @DeleteMapping
    public void deleteOldNotifications() {
        // add functionality here
    }
}