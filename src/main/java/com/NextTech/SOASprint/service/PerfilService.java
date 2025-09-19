package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilCreateDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilResponseDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilUpdateDTO;
import com.NextTech.SOASprint.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepo;

    @Transactional
    public Long criar(PerfilCreateDTO dto) {
        Perfil perfil = Perfil.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();

        return perfilRepo.save(perfil).getId();
    }

    @Transactional(readOnly = true)
    public Page<PerfilResponseDTO> listar(Pageable pageable) {
        return perfilRepo.findAll(pageable)
                .map(p -> new PerfilResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getDescricao()
                ));
    }

    @Transactional(readOnly = true)
    public PerfilResponseDTO getById(Long id) {
        Perfil p = perfilRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));
        return new PerfilResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao()
        );
    }

    @Transactional
    public void atualizar(Long id, PerfilUpdateDTO dto) {
        Perfil p = perfilRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));

        p.setNome(dto.nome());
        p.setDescricao(dto.descricao());

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
