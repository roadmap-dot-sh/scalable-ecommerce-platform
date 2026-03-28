package com.example.cartservice.client;

import com.example.cartservice.client.fallback.UserServiceFallback;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    @Value("${services.user.url:http://localhost:8081}")
    private String userServiceUrl;

    public boolean userExists(String userId) {
        try {
            webClient.get()
                    .uri(userServiceUrl + "/api/users/{id}", userId)
                    .retrieve()
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(3));
            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (Exception e) {
            return UserServiceFallback.assumeExists();
        }
    }
}
