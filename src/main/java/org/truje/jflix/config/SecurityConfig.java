package org.truje.jflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.truje.jflix.service.auth.DatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_URLS = {"/api/v1/auth/register", "/api/v1/auth/login"};

    @Bean
    public AuthenticationManager authenticationManager(
            DatabaseUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, AUTH_URLS)
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/auth/logout")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/**")
                        .permitAll() // for now just to random stuff
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

    // USE ONE OR THE OTHER, this works with JWT auth
    // @Bean
    public SecurityFilterChain securityFilterChainStateless(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, AUTH_URLS)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/**")
                        .permitAll() // for now just to random stuff
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
