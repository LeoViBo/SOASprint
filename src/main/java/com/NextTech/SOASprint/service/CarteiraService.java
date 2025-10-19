package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Carteira;
import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraCreateDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraResponseDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraUpdateDTO;
import com.NextTech.SOASprint.repository.CarteiraRepository;
import com.NextTech.SOASprint.repository.PerfilRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepo;
    private final PerfilRepository perfilRepo;

    // Injeção de dependência via construtor (preferida)
    public CarteiraService(CarteiraRepository carteiraRepo, PerfilRepository perfilRepo) {
        this.carteiraRepo = carteiraRepo;
        this.perfilRepo = perfilRepo;
    }

    @Transactional
    public Long criar(CarteiraCreateDTO dto) {
        // CORREÇÃO 1: Usando dto.perfilId() para acessar o campo do Record
        Perfil perfil = perfilRepo.findById(dto.perfilId())
                .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado"));

        // CORREÇÃO 2: Substituindo o uso do Lombok @Builder por construtor e setters
        Carteira carteira = new Carteira();
        carteira.setNome(dto.nome());
        carteira.setValorTotal(dto.valorTotal());
        carteira.setEstrategia(dto.estrategia());
        carteira.setAtivos(dto.ativos());
        carteira.setPerfil(perfil);

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
