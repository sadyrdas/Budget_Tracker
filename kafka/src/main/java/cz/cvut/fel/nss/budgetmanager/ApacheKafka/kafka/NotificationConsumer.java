package cz.cvut.fel.nss.budgetmanager.ApacheKafka.kafka;

import com.mashape.unirest.http.exceptions.UnirestException;
import cz.cvut.fel.nss.budgetmanager.ApacheKafka.service.SendEmail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class that consumes messages from the "SENDING_EMAIL" Kafka topic and sends notifications.
 */
@Service
@Slf4j
public class NotificationConsumer {

    /**
     * Kafka listener method for consuming email messages.
     *
     * @param email The email message payload.
     */
    @KafkaListener(topics = "SENDING_EMAIL", containerFactory = "mailNotificationListener")
    public void listener(@Payload String email){
        try {
            log.info("Send notification: ", SendEmail.sendEmail(email));
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
