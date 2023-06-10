package cz.cvut.fel.nss.budgetmanager.BudgetManager.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "SENDING_EMAIL";

    public void sendEmail(String email){
        this.kafkaTemplate.send(TOPIC, email);
    }
}
