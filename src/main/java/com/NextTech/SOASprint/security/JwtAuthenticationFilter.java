package com.NextTech.SOASprint.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filtro customizado que é executado uma vez por requisição para interceptar
 * o cabeçalho Authorization, extrair e validar o JWT.
 */
@Component // Marca como um componente para ser injetado nas SecurityConfigurations
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService; // Será sua classe AuthenticationService

    // Injeção de dependências do TokenService e do Service que carrega o usuário
    public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Recupera o token do cabeçalho da requisição
        Optional<String> tokenOptional = recoverToken(request);

        if (tokenOptional.isPresent()) {
            String token = tokenOptional.get();

            // 2. Extrai o ID de login (subject) do token
            Optional<String> loginIdOptional = tokenService.getSubject(token);

            if (loginIdOptional.isPresent()) {
                String loginId = loginIdOptional.get();

                // 3. Carrega os detalhes do usuário pelo ID de login (subject)
                UserDetails user = userDetailsService.loadUserByUsername(loginId);

                // 4. Cria o objeto de autenticação
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // 5. Define a autenticação no contexto do Spring Security
                // Isso sinaliza que a requisição está autenticada
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continua a cadeia de filtros (passa para o próximo filtro, ou para o Controller)
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho "Authorization: Bearer <token>"
     */
    private Optional<String> recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Retorna a string do token removendo o prefixo "Bearer "
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }
}