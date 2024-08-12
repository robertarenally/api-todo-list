package br.com.gestao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisTokenService {

    @Autowired
    private ReactiveRedisTemplate<String, JwtAuthenticationToken> redisTemplate;

    public Mono<Void> cacheToken(String key, JwtAuthenticationToken token) {
        return redisTemplate.opsForValue().set(key, token).then();
    }

    public Mono<JwtAuthenticationToken> retrieveToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
