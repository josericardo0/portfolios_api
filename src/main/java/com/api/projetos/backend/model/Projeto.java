package com.api.projetos.backend.model;

import com.api.projetos.backend.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataTerminoPrevisto;

    private LocalDate dataRealTermino;

    private BigDecimal orcamentoTotal;

    private String descricao;

    private Long gerenteId;

    @Enumerated(EnumType.STRING)
    private StatusProjeto statusProjeto;

    @ManyToMany
    private List<MembroProjeto> membros;



}
