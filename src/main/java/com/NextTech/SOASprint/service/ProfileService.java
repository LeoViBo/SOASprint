package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.ProfileRegistrationRequest; // DTO que será criado no próximo passo
import java.util.Optional;

public interface ProfileService {

    Perfil registerNewProfile(ProfileRegistrationRequest data);

    Optional<Perfil> findById(Long id);
}