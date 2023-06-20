package cz.cvut.fel.nss.budgetmanager.BudgetManager.service;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.repository.NotificationDao;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.Notification;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationDao notificationDao;

    /**
     * Constructs a new NotificationService with the provided NotificationDao.
     *
     * @param notificationDao The NotificationDao implementation used for data access.
     */
    @Autowired
    public NotificationService(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * Pushes a notification for a user.
     *
     * @param userId The ID of the user to receive the notification.
     * @param type   The type of the notification.
     * @param info   The information/details of the notification.
     */
    public void pushNotification(Long userId, NotificationType type, String info) {
        Notification notification = new Notification(
                Instant.now(),
                info,
                type,
                userId
        );

        notificationDao.save(notification);
    }

    /**
     * Retrieves the notifications for a user within the last week.
     *
     * @param userId The ID of the user.
     * @return The list of notifications for the user within the last week.
     */
    public List<Notification> getUserNotifications(String userId) {
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        return notificationDao.findAllByTimestampAfter(oneWeekAgo);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId The ID of the notification to delete.
     */
    public void deleteNotification(String notificationId) {
        notificationDao.deleteById(notificationId);
    }
}
