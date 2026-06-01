package com.api.projetos.backend.dto;

import com.api.projetos.backend.enums.NivelRiscoProjeto;
import com.api.projetos.backend.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ListagemProjetosResponse(

        UUID id,

        String nome,

        StatusProjeto statusProjeto,

        NivelRiscoProjeto nivelRiscoProjeto,

        BigDecimal orcamentoTotal,

        LocalDate dataInicio,

        LocalDate dataTerminoPrevisto

){}
