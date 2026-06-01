package com.api.projetos.backend;

import com.api.projetos.backend.enums.NivelRiscoProjeto;
import com.api.projetos.backend.model.Projeto;
import com.api.projetos.backend.service.CalculoRiscoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculoRiscoServiceTest {
    private final CalculoRiscoService service =
            new CalculoRiscoService();

    @Test
    void deveRetornarBaixoRisco() {

        Projeto projeto = new Projeto();

        projeto.setDataInicio(
                LocalDate.of(2026, 1, 1)
        );

        projeto.setDataTerminoPrevisto(
                LocalDate.of(2026, 3, 1)
        );

        projeto.setOrcamentoTotal(
                new BigDecimal("80000")
        );

        NivelRiscoProjeto risco =
                service.calculate(projeto);

        assertEquals(
                NivelRiscoProjeto.BAIXO,
                risco
        );
    }

    @Test
    void deveRetornarMedioRiscoPorOrcamento() {

        Projeto projeto = new Projeto();

        projeto.setDataInicio(LocalDate.now());
        projeto.setDataTerminoPrevisto(
                LocalDate.now().plusMonths(2)
        );

        projeto.setOrcamentoTotal(
                new BigDecimal("150000")
        );

        assertEquals(
                NivelRiscoProjeto.MEDIO,
                service.calculate(projeto)
        );
    }

    @Test
    void deveRetornarAltoRiscoPorPrazo() {

        Projeto projeto = new Projeto();

        projeto.setDataInicio(LocalDate.now());

        projeto.setDataTerminoPrevisto(
                LocalDate.now().plusMonths(8)
        );

        projeto.setOrcamentoTotal(
                new BigDecimal("90000")
        );

        assertEquals(
                NivelRiscoProjeto.ALTO,
                service.calculate(projeto)
        );
    }
}
