package com.diegoip.product.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .authorizeExchange(exchange -> exchange
                                .pathMatchers("/swagger-ui.html","/webjars/swagger-ui/**","/v3/api-docs/**").permitAll()
                                .anyExchange().authenticated()
                        ).httpBasic(withDefaults());
        return http.build();
    }
}
