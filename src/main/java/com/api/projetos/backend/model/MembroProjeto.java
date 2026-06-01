package com.api.projetos.backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "membro_projetos")
public class MembroProjeto {

    @Id
    private Long membroId;

    private String nome;

    private String funcao;

}
