package com.NextTech.SOASprint.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração principal para o Spring Security.
 * Define a cadeia de filtros, o tipo de sessão (stateless) e o encoder de senhas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfigurations(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Define a cadeia de filtros de segurança (SecurityFilterChain).
     * * @param http Objeto HttpSecurity para configurar a segurança.
     * @return O SecurityFilterChain configurado.
     * @throws Exception se ocorrer um erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita CSRF, que não é necessário para APIs REST stateless
                .csrf(csrf -> csrf.disable())

                // Define a política de sessão como STATELESS (sem estado), essencial para JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura as regras de autorização para os endpoints
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público a endpoints de autenticação e cadastro de perfil
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/profiles").permitAll() // Exemplo: permitir cadastro público
                        
                        // Permite acesso público ao Swagger/OpenAPI (documentação da API)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // Todas as outras requisições (GET, PUT, DELETE, etc.) requerem autenticação
                        .anyRequest().authenticated()
                )

                // Adiciona o filtro JWT customizado antes do filtro padrão de autenticação de usuário/senha
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * Expõe o AuthenticationManager como um Bean.
     * Será usado no AuthenticationController para realizar o login.
     * * @param configuration A configuração de autenticação do Spring.
     * @return O AuthenticationManager.
     * @throws Exception se ocorrer um erro.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Define o BCryptPasswordEncoder como o mecanismo de codificação de senhas.
     * Essencial para criptografar senhas de forma segura.
     * * @return O PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt é o padrão recomendado para hashing de senhas.
        return new BCryptPasswordEncoder();
    }
}