package cz.cvut.fel.nss.budgetmanager.BudgetManager.repository;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface NotificationDao extends MongoRepository<Notification, String> {

    @Query("{ 'timestamp' : { $gte: ?0 } }")
    List<Notification> findAllByTimestampAfter(Instant time);
}
