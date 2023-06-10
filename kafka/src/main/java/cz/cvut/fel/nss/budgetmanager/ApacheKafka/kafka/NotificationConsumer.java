package cz.cvut.fel.nss.budgetmanager.ApacheKafka.kafka;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

//    @KafkaListener(topics = "SENDING_EMAIL", containerFactory = "")
    public void listener(@Payload String email){
        //# TODO some logger here
        System.out.println("Send notification: ");
    }
}
