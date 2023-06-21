package cz.cvut.fel.nss.budgetmanager.Dispatcher.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Represents a notification entity.
 */
@Data
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Instant timestamp;
    private String info;
    private NotificationType type;
    private Long userId;
}
