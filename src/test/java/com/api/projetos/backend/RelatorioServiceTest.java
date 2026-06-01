package com.api.projetos.backend;


import com.api.projetos.backend.dto.RelatorioResponse;
import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.repository.ProjetoRepository;
import com.api.projetos.backend.service.RelatorioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @Mock
    private ProjetoRepository repository;

    @InjectMocks
    private RelatorioService service;

    @Test
    void deveGerarRelatorio() {

        when(repository.countProjetosPorStatus())
                .thenReturn(
                        List.<Object[]>of(
                                new Object[]{
                                        StatusProjeto.EM_ANALISE,
                                        2L
                                }
                        )
                );

        when(repository.sumOrcamentoPorStatus())
                .thenReturn(
                        List.<Object[]>of(
                                new Object[]{
                                        StatusProjeto.EM_ANALISE,
                                        new BigDecimal("100000")
                                }
                        )
                );

        when(repository.findByStatusProjeto(
                StatusProjeto.ENCERRADO
        )).thenReturn(List.of());

        when(repository.findAll())
                .thenReturn(List.of());

        RelatorioResponse response =
                service.gerarRelatorio();

        assertNotNull(response);
    }
}
