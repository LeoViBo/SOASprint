package com.NextTech.SOASprint.repository;

import com.NextTech.SOASprint.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

// A interface é completa com estas poucas linhas:
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring Data JPA automaticamente cria a query SQL
    // para "SELECT * FROM autenticacao_usuario WHERE email = ?"
    UserDetails findByEmail(String email);
}