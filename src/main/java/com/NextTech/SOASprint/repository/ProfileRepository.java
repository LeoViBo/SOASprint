package com.NextTech.SOASprint.repository;

import com.NextTech.SOASprint.domain.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ProfileRepository extends JpaRepository<Perfil, Long> {
}