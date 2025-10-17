package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Carteira;
import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraCreateDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraResponseDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraUpdateDTO;
import com.NextTech.SOASprint.repository.CarteiraRepository;
import com.NextTech.SOASprint.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepo;
    @Autowired
    private PerfilRepository perfilRepo;

    @Transactional
    public Long criar(CarteiraCreateDTO dto) {
        // Corrigido para dto.idPerfil()
        Perfil perfil = perfilRepo.findById(dto.idPerfil())
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));

        Carteira carteira = Carteira.builder()
                .nome(dto.nome())
                .valorTotal(dto.valorTotal())
                .estrategia(dto.estrategia())
                .ativos(dto.ativos())
                .perfil(perfil)
                .build();

        return carteiraRepo.save(carteira).getId();
    }

    @Transactional(readOnly = true)
    public Page<CarteiraResponseDTO> listar(Pageable pageable) {
        return carteiraRepo.findAll(pageable)
                .map(c -> new CarteiraResponseDTO(
                        c.getId(),
                        c.getNome(),
                        c.getValorTotal(),
                        c.getEstrategia(),
                        c.getAtivos(),
                        c.getPerfil().getId()
                ));
    }

    @Transactional(readOnly = true)
    public CarteiraResponseDTO getById(Long id) {
        Carteira c = carteiraRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Carteira não encontrada"));
        return new CarteiraResponseDTO(
                c.getId(),
                c.getNome(),
                c.getValorTotal(),
                c.getEstrategia(),
                c.getAtivos(),
                c.getPerfil().getId()
        );
    }

    @Transactional
    public void atualizar(Long id, CarteiraUpdateDTO dto) {
        Carteira c = carteiraRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Carteira não encontrada"));

        c.setNome(dto.nome());
        c.setValorTotal(dto.valorTotal());
        c.setEstrategia(dto.estrategia());
        c.setAtivos(dto.ativos());

        carteiraRepo.save(c);
    }

    @Transactional
    public void deletar(Long id) {
        if (!carteiraRepo.existsById(id)) {
            throw new NoSuchElementException("Carteira não encontrada");
        }
        carteiraRepo.deleteById(id);
    }
}
