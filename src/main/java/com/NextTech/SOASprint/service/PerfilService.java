package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.domain.model.Usuario; // <-- Importar a Entidade Usuario
import com.NextTech.SOASprint.domain.model.vo.EmailVO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilCreateDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilResponseDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilUpdateDTO;
import com.NextTech.SOASprint.repository.PerfilRepository;
import com.NextTech.SOASprint.repository.UsuarioRepository; // <-- Importar o Repositório de Usuario

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Importar o PasswordEncoder
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public PerfilService(PerfilRepository perfilRepo, UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.perfilRepo = perfilRepo;
        this.usuarioRepo = usuarioRepo; 
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long criar(PerfilCreateDTO dto) {
        if (perfilRepo.findByEmailValue(dto.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String senhaHash = passwordEncoder.encode(dto.password()); 

        Usuario novoUsuario = new Usuario(dto.email(), senhaHash); 
        
        usuarioRepo.save(novoUsuario); 

        Perfil perfil = new Perfil();
        perfil.setNome(dto.nome());
        perfil.setEmail(new EmailVO(dto.email()));

        perfil.setObjetivoFinanceiro(dto.objetivoFinanceiro());
        perfil.setToleranciaRisco(dto.toleranciaRisco());
        perfil.setValorParaInvesimento(dto.valorParaInvestimento().doubleValue());
        perfil.setHorizonteDeTempo(dto.horizonteDeTempo());

        return perfilRepo.save(perfil).getId();
    }

    @Transactional(readOnly = true)
    public Page<PerfilResponseDTO> listar(Pageable pageable) {
        return perfilRepo.findAll(pageable)
                .map(p -> new PerfilResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getEmail().getValue(),
                        p.getObjetivoFinanceiro(),
                        p.getToleranciaRisco(),
                        java.math.BigDecimal.valueOf(p.getValorParaInvesimento()),
                        p.getHorizonteDeTempo(),
                        p.getCarteiras().stream().map(c -> c.getId()).collect(Collectors.toList())
                ));
    }

    @Transactional(readOnly = true)
    public PerfilResponseDTO getById(Long id) {
        Perfil p = perfilRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));
        return new PerfilResponseDTO(
                p.getId(),
                p.getNome(),
                p.getEmail().getValue(),
                p.getObjetivoFinanceiro(),
                p.getToleranciaRisco(),
                java.math.BigDecimal.valueOf(p.getValorParaInvesimento()),
                p.getHorizonteDeTempo(),
                p.getCarteiras().stream().map(c -> c.getId()).collect(Collectors.toList())
        );
    }

    @Transactional
    public void atualizar(Long id, PerfilUpdateDTO dto) {
        Perfil p = perfilRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));
        
        // Verifica se o email está sendo alterado e se o novo email já existe
        if (!p.getEmail().getValue().equals(dto.email()) && perfilRepo.findByEmailValue(dto.email()).isPresent()) {
            throw new IllegalArgumentException("O novo e-mail já está em uso por outro perfil.");
        }

        p.setNome(dto.nome());
        p.setEmail(new EmailVO(dto.email()));
        p.setObjetivoFinanceiro(dto.objetivoFinanceiro());
        p.setToleranciaRisco(dto.toleranciaRisco());
        p.setValorParaInvesimento(dto.valorParaInvestimento().doubleValue());
        p.setHorizonteDeTempo(dto.horizonteDeTempo());

        perfilRepo.save(p);
    }

    @Transactional
    public void deletar(Long id) {
        if (!perfilRepo.existsById(id)) {
            throw new NoSuchElementException("Perfil não encontrado");
        }
        perfilRepo.deleteById(id);
    }
}