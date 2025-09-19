package com.NextTech.SOASprint.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.NextTech.SOASprint.domain.model.vo.EmailVO;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="usuario")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Perfil {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Embedded
    private EmailVO email; 

    @Column(nullable = false)
    private String nome; 

    // "Aposentadoria", "Reserva de emergência", "Compra de imóvel".
    @Column(nullable = false)
    private String objetivoFinanceiro; 

    // "Conservador", "Moderado", "Arrojado".
    @Column(nullable = false)
    private String toleranciaRisco; 

    // Indica quanto o investidor pretende destinar para investir.
    @Column(nullable = false)
    private double valorParaInvesimento; 

    // Prazo (em anos) que o investidor pretende manter os investimentos.
    @Column(nullable = false)
    private int horizonteDeTempo; 

    @OneToMany(mappedBy= "usuario")
    private List<Carteira> carteiras = new ArrayList<>();
}