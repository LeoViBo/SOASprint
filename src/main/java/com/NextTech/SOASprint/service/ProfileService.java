package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.ProfileRegistrationRequest; // DTO que será criado no próximo passo
import java.util.Optional;

/**
 * Interface que define os métodos de negócio da entidade Perfil.
 * Permite que a implementação seja trocada sem alterar o Controller (DIP/OCP).
 */
public interface ProfileService {

    /**
     * Registra um novo Perfil e seu respectivo Usuário de autenticação.
     * @param data DTO com dados de Perfil e credenciais.
     * @return O Perfil recém-criado.
     */
    Perfil registerNewProfile(ProfileRegistrationRequest data);

    /**
     * Busca um Perfil pelo seu ID.
     * @param id O ID do Perfil.
     * @return Um Optional contendo o Perfil, se encontrado.
     */
    Optional<Perfil> findById(Long id);
    
    // Adicione aqui outros métodos de CRUD ou de negócio para Perfil
    // Perfil updateProfile(Long id, ProfileUpdateRequest data);
}