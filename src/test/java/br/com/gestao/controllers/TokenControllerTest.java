package br.com.gestao.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.gestao.services.RedisTokenService;
import reactor.core.publisher.Mono;

public class TokenControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private RedisTokenService redisTokenService;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(tokenController).build();
    }

    @Test
    void testStoreToken() {
        // Arrange
        JwtAuthenticationToken token = new JwtAuthenticationToken(null, null, null);
        String key = "test-key";
        when(redisTokenService.cacheToken(eq(key), any(JwtAuthenticationToken.class))).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.post()
                .uri("/api/tokens/store")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(token))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testRetrieveToken() {
        // Arrange
        JwtAuthenticationToken token = new JwtAuthenticationToken(null, null, null);
        String key = "test-key";
        when(redisTokenService.retrieveToken(eq(key))).thenReturn(Mono.just(token));

        // Act & Assert
        webTestClient.get()
                .uri("/api/tokens/retrieve?key=" + key)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtAuthenticationToken.class)
                .isEqualTo(token);
    }
}
