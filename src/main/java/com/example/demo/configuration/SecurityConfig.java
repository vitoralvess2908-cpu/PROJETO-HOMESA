package com.example.demo.configuration;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableMethodSecurity // ou @EnableGlobalMethodSecurity(prePostEnabled = true) em versões anteriores
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // forte — recomendado em docs.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //restrige os metodos post da da rota imoveis
                        .requestMatchers(HttpMethod.POST, "/imoveis/cadastroImovel").hasRole("PROPRIETARIO")
                        .requestMatchers("/usuarios/meProprietario").authenticated()
                        .requestMatchers("/usuarios/meCliente").authenticated()
                        .requestMatchers(HttpMethod.GET, "/usuarios/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imoveis/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pesquisa/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/compra/**").hasRole("CLIENTE")
                        .requestMatchers("/auth/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                );

        // Se usar H2 console (dev), liberar frameOptions:
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
