package com.api.projetos.backend.dto;

import com.api.projetos.backend.enums.NivelRiscoProjeto;
import com.api.projetos.backend.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProjetoResponse(

        UUID id,

        String nome,

        LocalDate dataInicio,

        LocalDate dataTerminoPrevisto,

        LocalDate dataRealTermino,

        BigDecimal orcamentoTotal,

        String descricao,

        Long gerenteId,

        String nomeGerente,

        StatusProjeto statusProjeto,

        NivelRiscoProjeto nivelRiscoProjeto,

        List<MembroResponse> membros

){}



