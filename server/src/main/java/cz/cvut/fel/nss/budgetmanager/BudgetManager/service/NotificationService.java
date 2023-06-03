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

    @Autowired
    public NotificationService(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public void pushNotification(Long userId, NotificationType type, String info) {
        Notification notification = new Notification(
                Instant.now(),
                info,
                type,
                userId
        );

        notificationDao.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        return notificationDao.findAllByTimestampAfter(oneWeekAgo);
    }

    public void deleteNotification(String notificationId) {
        notificationDao.deleteById(notificationId);
    }
}
