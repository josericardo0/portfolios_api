package com.api.projetos.backend.dto;

import java.math.BigDecimal;
import java.util.Map;

public record RelatorioResponse(

        Map<String, Long>  projetosPorStatus,

        Map<String, BigDecimal> orcamentoPorStatus,

        Double mediaDuracaoProjetosEncerrados,

        Long membrosAssociados
){}
