package com.NextTech.SOASprint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NextTech.SOASprint.domain.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByEmailValue(String email);
}
