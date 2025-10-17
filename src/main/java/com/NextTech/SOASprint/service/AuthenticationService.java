package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço que implementa a interface UserDetailsService do Spring Security.
 * É responsável por carregar os detalhes do usuário (UserDetails) 
 * a partir do banco de dados pelo identificador de login (username/email).
 */
@Service
public class AuthenticationService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Injeção de dependência do repositório de autenticação
    public AuthenticationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método central do Spring Security. 
     * Ele recebe o "username" (que no nosso caso é o email) e 
     * deve retornar a entidade UserDetails correspondente.
     * * @param username O identificador de login (email) fornecido.
     * @return Os detalhes do usuário (sua entidade Usuario).
     * @throws UsernameNotFoundException se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // O método findByEmail é o Query Method que criamos no UsuarioRepository
        UserDetails usuario = usuarioRepository.findByEmail(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }
        
        return usuario;
    }
}