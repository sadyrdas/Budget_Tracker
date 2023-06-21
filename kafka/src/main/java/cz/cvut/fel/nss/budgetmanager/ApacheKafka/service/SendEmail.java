package cz.cvut.fel.nss.budgetmanager.ApacheKafka.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for sending email notifications.
 */
@Slf4j
@Service
public class SendEmail {

    /**
     * The domain name for the Mailgun API.
     */
    private static final String DOMAIN_NAME = "sandboxa5cfe50ef0d94c56a44688512e1b2b4e.mailgun.org";

    /**
     * Sends an email notification using the Mailgun API.
     *
     * @param email The email address to send the notification to.
     * @return The JSON response from the Mailgun API.
     * @throws UnirestException if an error occurs during the API request.
     */
    public static JsonNode sendEmail(String email) throws UnirestException{
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                .basicAuth("api", MailgunKey.API_KEY)
                .queryString("from", "budgetManager@blob.te")
                .queryString("to", "ramir.velikij@gmail.com")
                .queryString("subject", "Budget limit is exceeded")
                .queryString("text", "Your budget limit is exceeded")
                .asJson();
        log.info("Mailgun send email about exceeding of budget limit to email ->" + email);
        return request.getBody();
    }

}
