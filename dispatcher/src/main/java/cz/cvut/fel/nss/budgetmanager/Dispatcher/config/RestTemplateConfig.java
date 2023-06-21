package cz.cvut.fel.nss.budgetmanager.Dispatcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${spring.security.user.name}@test.test")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    /**
     * Creates a RestTemplate bean with basic authentication.
     *
     * @return The configured RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new BasicAuthorizationInterceptor(username, password));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    /**
     * Custom request interceptor for adding basic authentication headers to the request.
     */
    public static class BasicAuthorizationInterceptor implements ClientHttpRequestInterceptor {

        private final String username;
        private final String password;

        /**
         * Constructs the BasicAuthorizationInterceptor with the given username and password.
         *
         * @param username The username for basic authentication.
         * @param password The password for basic authentication.
         */
        public BasicAuthorizationInterceptor(String username, String password) {
            this.username = username;
            this.password = password;
        }

        /**
         * Intercepts the request and adds the basic authentication headers.
         *
         * @param request   The request to be intercepted.
         * @param body      The request body.
         * @param execution The execution of the request.
         * @return The response of the executed request.
         * @throws IOException if an I/O error occurs.
         */
        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.setBasicAuth(username, password);
            return execution.execute(request, body);
        }
    }
}
