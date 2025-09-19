package com.NextTech.SOASprint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NextTech.SOASprint.domain.model.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    Optional<Carteira> findByNome(String nome);
}
