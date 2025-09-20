package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Carteira;
import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraCreateDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraResponseDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraUpdateDTO;
import com.NextTech.SOASprint.repository.CarteiraRepository;
import com.NextTech.SOASprint.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepo;
    private final PerfilRepository perfilRepo;

    @Transactional
    public Long criar(CarteiraCreateDTO dto) {
        // garante que a carteira vai estar associada a um perfil válido
        Perfil perfil = perfilRepo.findById(dto.perfilId())
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));

        Carteira carteira = Carteira.builder()
                .nome(dto.nome())
                .valorTotal(dto.valorTotal()) // Now correctly handles BigDecimal
                .estrategia(dto.estrategia())
                .ativos(dto.ativos())
                .usuario(perfil)
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
                        c.getUsuario().getId()
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
                c.getUsuario().getId()
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
