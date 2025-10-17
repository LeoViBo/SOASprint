package com.NextTech.SOASprint.security;

import com.NextTech.SOASprint.domain.model.Usuario; // Importa a nova entidade Usuario
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Serviço responsável por gerar (assinar) e validar (parsear) o JSON Web Token (JWT).
 */
@Service
public class TokenService {

    // Injeta a chave secreta do application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Injeta o tempo de expiração do token (em milissegundos)
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Gera o JWT a partir de um objeto Usuario.
     * @param usuario O usuário autenticado (entidade Usuario).
     * @return O token JWT em formato String.
     */
    public String generateToken(Usuario usuario) {
        // A chave secreta deve ser convertida em um objeto Key segura
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        Instant now = Instant.now();
        Date expirationDate = Date.from(now.plus(expiration, ChronoUnit.MILLIS));
        
        return Jwts.builder()
                // Define o emissor (quem criou o token)
                .setIssuer("API SOASprint")
                // Define o "subject" (o principal, neste caso, o email/username do usuário)
                // Usamos getUsername() pois implementa UserDetails
                .setSubject(usuario.getUsername()) 
                // Define a data de emissão (quando o token foi criado)
                .setIssuedAt(Date.from(now))
                // Define a data de expiração
                .setExpiration(expirationDate)
                // Assina o token com a chave secreta e o algoritmo HMAC SHA-256
                .signWith(key)
                .compact();
    }

    /**
     * Obtém o "subject" (o email/username do usuário) a partir do token.
     * @param token O token JWT.
     * @return O email de login (subject) do token, se válido.
     */
    public Optional<String> getSubject(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            
            // Faz o parsing do token e valida a assinatura
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Retorna o subject (ID de login)
            return Optional.ofNullable(claims.getSubject());

        } catch (Exception e) {
            // Se o token for inválido, expirado ou malformado, retorna vazio
            // Em produção, é bom logar o erro (e)
            return Optional.empty();
        }
    }
}