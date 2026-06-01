package com.api.projetos.backend.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizarProjetoRequest(

        String nome,

        LocalDate dataTerminoPrevisto,

        LocalDate dataRealTermino,

        @Positive
        BigDecimal orcamentoTotal,

        String descricao
) {}
