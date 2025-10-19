package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.domain.model.vo.EmailVO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilCreateDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilResponseDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilUpdateDTO;
import com.NextTech.SOASprint.repository.PerfilRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepo; 

    // Injeção de dependência via construtor (melhor prática e consistência)
    public PerfilService(PerfilRepository perfilRepo) {
        this.perfilRepo = perfilRepo;
    }

    @Transactional
    public Long criar(PerfilCreateDTO dto) {
        if (perfilRepo.findByEmailValue(dto.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        // Correção para substituir Perfil.builder() por construtor padrão e setters
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
