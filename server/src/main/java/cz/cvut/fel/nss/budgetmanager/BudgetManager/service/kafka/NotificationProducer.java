package cz.cvut.fel.nss.budgetmanager.BudgetManager.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for producing notifications by sending emails using Kafka.
 */
@Service
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "SENDING_EMAIL";

    /**
     * Sends an email notification by publishing the email to the Kafka topic.
     *
     * @param email The email to be sent.
     */
    public void sendEmail(String email){
        this.kafkaTemplate.send(TOPIC, email);
    }
}
