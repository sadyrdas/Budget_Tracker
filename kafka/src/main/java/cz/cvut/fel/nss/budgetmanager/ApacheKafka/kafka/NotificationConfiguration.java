package cz.cvut.fel.nss.budgetmanager.ApacheKafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Configuration class for Kafka notification settings.
 */
@EnableKafka
@Configuration
public class NotificationConfiguration {

    /**
     * The server port for Kafka connection.
     */
    private final String serverPort = "localhost:9092";

    /**
     * The group ID for the mail consumer.
     */
    private final String groupId = "SEND_MAIL_ID";

    /**
     * Creates a Kafka consumer factory for mail notifications.
     *
     * @return The created ConsumerFactory instance.
     */
    @Bean
    public ConsumerFactory<String, String> mailConsumerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverPort);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        properties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new StringDeserializer());
    }

    /**
     * Creates a ConcurrentKafkaListenerContainerFactory for mail notifications.
     *
     * @return The created ConcurrentKafkaListenerContainerFactory instance.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> mailNotificationListener(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(mailConsumerFactory());
        return factory;
    }

}
