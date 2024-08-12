package br.com.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import br.com.gestao.services.RedisTokenService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @Autowired
    private RedisTokenService redisTokenService;

    @PostMapping("/store")
    public Mono<Void> storeToken(@RequestParam String key, @RequestBody JwtAuthenticationToken token) {
        return redisTokenService.cacheToken(key, token);
    }

    @GetMapping("/retrieve")
    public Mono<JwtAuthenticationToken> retrieveToken(@RequestParam String key) {
        return redisTokenService.retrieveToken(key);
    }
}
