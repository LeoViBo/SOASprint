package com.NextTech.SOASprint.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarteiraDTOs {

    public record CarteiraCreateDTO(
            @NotBlank String nome,
            @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal valorTotal,
            @NotBlank String estrategia,
            @NotNull List<String> ativos
    ) {}

    public record CarteiraResponseDTO(
            Long id,
            String nome,
            BigDecimal valorTotal,
            String estrategia,
            List<String> ativos,
            Long perfilId
    ) {}

    public record CarteiraUpdateDTO(
            @NotBlank String nome,
            @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal valorTotal,
            @NotBlank String estrategia,
            @NotNull List<String> ativos
    ) {}
}
