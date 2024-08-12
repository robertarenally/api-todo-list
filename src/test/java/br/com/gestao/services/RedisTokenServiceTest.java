package br.com.gestao.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RedisTokenServiceTest {

    @Mock
    private ReactiveRedisTemplate<String, JwtAuthenticationToken> redisTemplate;

    @InjectMocks
    private RedisTokenService redisTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCacheToken() {
        // Arrange
        JwtAuthenticationToken token = new JwtAuthenticationToken(null, null, null);
        String key = "test-key";
        when(redisTemplate.opsForValue().set(eq(key), any(JwtAuthenticationToken.class)))
            .thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(redisTokenService.cacheToken(key, token))
            .expectComplete()
            .verify();
    }

    @Test
    void testRetrieveToken() {
        // Arrange
        JwtAuthenticationToken token = new JwtAuthenticationToken(null, null, null);
        String key = "test-key";
        when(redisTemplate.opsForValue().get(eq(key)))
            .thenReturn(Mono.just(token));

        // Act & Assert
        StepVerifier.create(redisTokenService.retrieveToken(key))
            .expectNext(token)
            .expectComplete()
            .verify();
    }
}