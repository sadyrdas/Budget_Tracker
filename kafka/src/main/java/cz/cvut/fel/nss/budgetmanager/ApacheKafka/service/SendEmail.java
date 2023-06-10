package cz.cvut.fel.nss.budgetmanager.ApacheKafka.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmail {

    private static final String DOMAIN_NAME = "sandboxa3c51e1c019a45d9bef1ae7667ad9a2a.mailgun.org";

    public static JsonNode sendEmail(String email) throws UnirestException{
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                .basicAuth("api", MailgunKey.API_KEY)
                .queryString("from", "remelNotification@test.com")
                .queryString("to", email)
                .queryString("subject", "New room reservation")
                .queryString("text", "Your room reservation is successful")
                .asJson();
        log.info("Mailgun send email about reservation to email ->" + email);
        return request.getBody();
    }

}
