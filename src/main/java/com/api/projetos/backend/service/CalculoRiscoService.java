package com.api.projetos.backend.service;


import com.api.projetos.backend.enums.NivelRiscoProjeto;
import com.api.projetos.backend.model.Projeto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class CalculoRiscoService {

    public NivelRiscoProjeto calculate(Projeto projeto) {

        long meses =
                ChronoUnit.MONTHS.between(
                        projeto.getDataInicio(),
                        projeto.getDataTerminoPrevisto()
                );

        BigDecimal orcamento = projeto.getOrcamentoTotal();

        if (
                orcamento.compareTo(new BigDecimal("500000")) > 0
                        || meses > 6
        ) {
            return NivelRiscoProjeto.ALTO;
        }

        if (
                orcamento.compareTo(new BigDecimal("100000")) > 0
                        || (meses > 3 && meses <= 6)
        ) {
            return NivelRiscoProjeto.MEDIO;
        }

        return NivelRiscoProjeto.BAIXO;
    }
}
