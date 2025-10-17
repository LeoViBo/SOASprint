package com.NextTech.SOASprint.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidade de Autenticação. Armazena as credenciais e implementa UserDetails.
 * Usa o Email (String) como login.
 */
@Entity
@Table(name = "AUTENTICACAO_USUARIO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usaremos o email do Perfil como o identificador de login (username)
    @Column(unique = true, nullable = false)
    private String email; 

    // Senha criptografada (BCrypt)
    @Column(nullable = false)
    private String password; 
    
    // Construtor para facilitar o registro de novos usuários
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- Métodos de UserDetails ---
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define a Role padrão para todos os usuários
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // O email é o identificador de login para o Spring Security
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}