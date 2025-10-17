package com.NextTech.SOASprint.repository;

import java.util.List; // Novo Import
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NextTech.SOASprint.domain.model.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    
    // MÉTODO ORIGINAL: Mantido porque é uma regra de negócio ou funcionalidade existente
    Optional<Carteira> findByNome(String nome);
    
    // MÉTODO NOVO: Necessário para a lógica de negócio (ex: WalletServiceImpl)
    // Busca todas as carteiras pertencentes a um determinado Perfil
    List<Carteira> findByPerfilId(Long perfilId);
}