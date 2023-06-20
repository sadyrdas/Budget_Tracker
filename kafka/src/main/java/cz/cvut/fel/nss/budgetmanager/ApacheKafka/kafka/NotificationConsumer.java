package cz.cvut.fel.nss.budgetmanager.ApacheKafka.kafka;

import com.mashape.unirest.http.exceptions.UnirestException;
import cz.cvut.fel.nss.budgetmanager.ApacheKafka.service.SendEmail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "SENDING_EMAIL", containerFactory = "mailNotificationListener")
    public void listener(@Payload String email){
        try {
            log.info("Send notification: ", SendEmail.sendEmail(email));
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
