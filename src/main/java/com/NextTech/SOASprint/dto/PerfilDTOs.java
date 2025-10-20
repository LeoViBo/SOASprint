package com.NextTech.SOASprint.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PerfilDTOs {

    public record PerfilCreateDTO(
            @NotBlank String nome,
            @NotBlank @Email String email,
            
            @NotBlank 
            @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
            String password, 
            
            @NotBlank String objetivoFinanceiro,
            @NotBlank String toleranciaRisco,
            @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal valorParaInvestimento,
            @Min(1) int horizonteDeTempo
    ) {}

    public record PerfilResponseDTO(
            Long id,
            String nome,
            String email,
            String objetivoFinanceiro,
            String toleranciaRisco,
            BigDecimal valorParaInvestimento,
            int horizonteDeTempo,
            List<Long> carteirasIds
    ) {}

    public record PerfilUpdateDTO(
            @NotBlank String nome,
            @NotBlank @Email String email,
            @NotBlank String objetivoFinanceiro,
            @NotBlank String toleranciaRisco,
            @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal valorParaInvestimento,
            @Min(1) int horizonteDeTempo
    ) {}
}