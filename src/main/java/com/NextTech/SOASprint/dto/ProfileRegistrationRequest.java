package com.NextTech.SOASprint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// DTO para receber todos os dados de cadastro, incluindo as credenciais
@Getter
@Setter
public class ProfileRegistrationRequest {

    // Credenciais para o Usuario
    @NotBlank
    @Email
    private String email; 

    @NotBlank
    private String password;
    
    // Dados para o Perfil
    @NotBlank
    private String nome;

    @NotBlank
    private String objetivoFinanceiro; 

    @NotBlank
    private String toleranciaRisco; 

    @NotNull
    @Min(value = 0, message = "O valor para investimento deve ser positivo")
    private double valorParaInvesimento; 

    @NotNull
    @Min(value = 1, message = "O horizonte de tempo deve ser de no mínimo 1 ano")
    private int horizonteDeTempo;
}