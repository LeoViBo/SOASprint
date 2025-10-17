package com.NextTech.SOASprint.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarteiraDTOs {

    /**
     * DTO de Criação de Carteira.
     * REMOÇÃO: O perfilId foi removido, pois deve ser obtido do token JWT do usuário autenticado.
     */
    public record CarteiraCreateDTO(
            @NotBlank String nome,
            @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal valorTotal,
            @NotBlank String estrategia,
            @NotNull List<String> ativos
            // Long perfilId REMOVIDO
    ) {}

    /**
     * DTO de Resposta de Carteira.
     * O campo foi renomeado para 'perfilId' para refletir o ID do perfil associado.
     */
    public record CarteiraResponseDTO(
            Long id,
            String nome,
            BigDecimal valorTotal,
            String estrategia,
            List<String> ativos,
            Long perfilId // Mantido, para o cliente saber a quem pertence (vindo da entidade Carteira)
    ) {}

    /**
     * DTO de Atualização de Carteira.
     * Nenhuma alteração necessária aqui, pois as credenciais são validadas pelo ID da rota.
     */
    public record CarteiraUpdateDTO(
            @NotBlank String nome,
            @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal valorTotal,
            @NotBlank String estrategia,
            @NotNull List<String> ativos
    ) {}
}