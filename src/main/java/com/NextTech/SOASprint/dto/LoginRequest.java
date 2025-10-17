package com.NextTech.SOASprint.dto;

import jakarta.validation.constraints.NotBlank;

// Usando Lombok para getters/setters/construtores, se você o tiver configurado
import lombok.Getter; 
import lombok.Setter; 

@Getter
@Setter
public class LoginRequest {
    
    @NotBlank // Garante que o campo não é nulo nem vazio
    private String loginId; // O email ou username do usuário
    
    @NotBlank
    private String password;
}