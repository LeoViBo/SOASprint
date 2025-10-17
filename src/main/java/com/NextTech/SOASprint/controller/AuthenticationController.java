package com.NextTech.SOASprint.controller;

import com.NextTech.SOASprint.dto.LoginRequest;
import com.NextTech.SOASprint.dto.LoginResponse;
import com.NextTech.SOASprint.model.User;
import com.NextTech.SOASprint.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pelos endpoints de autenticação (Login).
 */
@RestController
@RequestMapping("/auth") // Define o caminho base para /auth
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    // Injeção de dependências
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest data) {
        try {
            // 1. Cria um token de autenticação com as credenciais recebidas
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(data.getLoginId(), data.getPassword());

            // 2. Tenta autenticar o usuário. O Spring fará:
            //    - Chamar o AuthenticationService (UserDetailsService) para carregar o usuário
            //    - Comparar a senha (usando BCryptPasswordEncoder)
            Authentication authentication = this.authenticationManager.authenticate(authToken);

            // 3. Se a autenticação for bem-sucedida, gera o JWT
            String token = tokenService.generateToken((User) authentication.getPrincipal());

            // 4. Retorna o token na resposta
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (AuthenticationException e) {
            // Se a autenticação falhar (senha incorreta, usuário não encontrado), retorna 403 Forbidden ou 401 Unauthorized
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }
    }
}