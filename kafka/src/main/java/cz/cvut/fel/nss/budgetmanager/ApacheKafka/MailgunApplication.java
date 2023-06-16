package cz.cvut.fel.nss.budgetmanager.ApacheKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = {
                "cz.cvut.fel.nss.budgetmanager.ApacheKafka"
        },
        exclude = {SecurityAutoConfiguration.class}
)
public class MailgunApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailgunApplication.class, args);
    }
}
