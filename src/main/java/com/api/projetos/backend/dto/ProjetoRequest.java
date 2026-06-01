package com.api.projetos.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjetoRequest(

    @NotBlank
    String nome,

    @NotNull
    LocalDate dataInicio,

    @NotNull
    LocalDate dataTerminoPrevisto,

    @Positive
    BigDecimal orcamentoTotal,

    @NotBlank
    String descricao,

    @NotNull
    Long gerenteId,

    @Size(min = 1, max = 10)
    List<Long> membrosIds
) {}
