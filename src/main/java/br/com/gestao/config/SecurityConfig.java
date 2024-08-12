package br.com.gestao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated()).oauth2Login(); // Configuração do
																									// login OAuth2

		return http.build();
	}
}