package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Definimos BCrypt como el motor para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Exponemos el AuthenticationManager, que será el encargado de validar las credenciales más adelante
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Agrega este nuevo Bean para enlazar el servicio y el encriptador
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        // Pasamos el userDetailsService directamente al constructor
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

        // El encriptador de contraseñas sí se sigue asignando con el método set
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    // 3. Configuramos las reglas principales de seguridad
    // Ojo, al quitar esto el sitio web vuelve a pedir credenciales
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF ya que usaremos tokens JWT (las APIs REST son inmunes a este ataque de forma natural con tokens)
                .csrf(csrf -> csrf.disable())

                // Indicamos que nuestra API no manejará sesiones en memoria (STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configuramos qué rutas son públicas y cuáles son privadas
                .authorizeHttpRequests(auth -> auth
                        // Agregamos /auth/** y /error a la lista de permitidos
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/auth/**", "/error").permitAll()
                        .anyRequest().authenticated() // Bloqueamos todo lo demás
                );

        return http.build();
    }
}