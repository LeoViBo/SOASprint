package com.NextTech.SOASprint.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter; 
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carteira")
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter 
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Perfil perfil; 

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false, length = 50)
    private String estrategia;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "carteira_ativos", joinColumns = @JoinColumn(name = "carteira_id"))
    @Column(name = "ativo")
    private List<String> ativos = new ArrayList<>();
}