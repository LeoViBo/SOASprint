package com.NextTech.SOASprint.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carteira")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    // MUDANÇA AQUI: Renomeado para 'perfil' para refletir a entidade referenciada (Perfil)
    private Perfil perfil; 

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    // "Conservadora", "Moderada", "Arrojada".
    @Column(nullable = false, length = 50)
    private String estrategia;

    // "Renda fixa", "Ações", "Fundos Imobiliários".
    @ElementCollection
    @CollectionTable(name = "carteira_ativos", joinColumns = @JoinColumn(name = "carteira_id"))
    @Column(name = "ativo")
    private List<String> ativos = new ArrayList<>();

}