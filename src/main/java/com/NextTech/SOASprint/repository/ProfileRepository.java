package com.NextTech.SOASprint.repository;

import com.NextTech.SOASprint.domain.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Repository para a entidade de negócio Perfil.
 * Estende JpaRepository para fornecer todas as operações CRUD básicas
 * e de persistência do Spring Data JPA.
 */
@Repository // Anotação opcional, mas recomendada para clareza
public interface ProfileRepository extends JpaRepository<Perfil, Long> {
    
    // O Spring Data JPA automaticamente fornece métodos como:
    // - save(Perfil perfil)
    // - findById(Long id)
    // - findAll()
    // - delete(Perfil perfil)
    
    // Você pode adicionar Query Methods específicos aqui, se necessário.
    // Exemplo: List<Perfil> findByObjetivoFinanceiro(String objetivo);
}