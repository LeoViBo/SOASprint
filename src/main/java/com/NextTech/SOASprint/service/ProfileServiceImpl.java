package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.domain.model.Usuario;
import com.NextTech.SOASprint.domain.model.vo.EmailVO;
import com.NextTech.SOASprint.dto.ProfileRegistrationRequest;
import com.NextTech.SOASprint.repository.PerfilRepository;
import com.NextTech.SOASprint.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementação concreta da interface ProfileService.
 * Contém a lógica de negócio e as transações de persistência.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository; // Novo: Para salvar credenciais
    private final PasswordEncoder passwordEncoder;     // Novo: Para criptografar senhas

    public ProfileServiceImpl(PerfilRepository perfilRepository,
                              UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método para registrar um novo Perfil e criar o registro de Autenticação (Usuario).
     * Garante que a senha seja criptografada antes de ser salva.
     */
    @Override
    @Transactional
    public Perfil registerNewProfile(ProfileRegistrationRequest data) {
        // 1. Criptografar a Senha
        String hashedPassword = passwordEncoder.encode(data.getPassword());

        // 2. Criar e Salvar a Entidade de Autenticação (Usuario)
        // Usamos o email do DTO para o login
        Usuario novoUsuario = new Usuario(data.getEmail(), hashedPassword);
        usuarioRepository.save(novoUsuario);
        
        // **OPCIONAL: Lidar com exceções de email duplicado aqui**

        // 3. Criar e Salvar a Entidade de Negócio (Perfil)
        Perfil novoPerfil = Perfil.builder()
                .email(new EmailVO(data.getEmail()))
                .nome(data.getNome())
                .objetivoFinanceiro(data.getObjetivoFinanceiro())
                .toleranciaRisco(data.getToleranciaRisco())
                .valorParaInvesimento(data.getValorParaInvesimento())
                .horizonteDeTempo(data.getHorizonteDeTempo())
                .build();

        return perfilRepository.save(novoPerfil);
    }
    
    // Método herdado da interface
    @Override
    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }
    
    // Mantenha aqui todo o código de negócio antigo de manipulação de Perfil.
}