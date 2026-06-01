package com.api.projetos.backend.service;

import com.api.projetos.backend.dto.RelatorioResponse;
import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.model.MembroProjeto;
import com.api.projetos.backend.model.Projeto;
import com.api.projetos.backend.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final ProjetoRepository projetoRepository;

    public RelatorioService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public RelatorioResponse gerarRelatorio() {

        Map<String, Long> projetosPorStatus =
                calcularProjetosPorStatus();

        Map<String, BigDecimal> orcamentoPorStatus =
                calcularOrcamentoPorStatus();

        Double mediaDuracaoProjetosEncerrados =
                calcularMediaDuracaoProjetosEncerrados();

        Long membrosAssociados =
                calcularMembrosUnicosAssociados();

        return new RelatorioResponse(
                projetosPorStatus,
                orcamentoPorStatus,
                mediaDuracaoProjetosEncerrados,
                membrosAssociados
        );
    }

    private Map<String, Long> calcularProjetosPorStatus() {

        return projetoRepository
                .countProjetosPorStatus()
                .stream()
                .collect(Collectors.toMap(
                        result -> ((StatusProjeto) result[0]).name(),
                        result -> (Long) result[1]
                ));
    }

    private Map<String, BigDecimal> calcularOrcamentoPorStatus() {

        return projetoRepository
                .sumOrcamentoPorStatus()
                .stream()
                .collect(Collectors.toMap(
                        result -> ((StatusProjeto) result[0]).name(),
                        result -> (BigDecimal) result[1]
                ));
    }

    private Double calcularMediaDuracaoProjetosEncerrados() {

        List<Projeto> encerrados =
                projetoRepository.findByStatusProjeto(
                        StatusProjeto.ENCERRADO
                );

        return encerrados.stream()
                .filter(p ->
                        p.getDataInicio() != null
                                && p.getDataRealTermino() != null
                )
                .mapToLong(p ->
                        ChronoUnit.DAYS.between(
                                p.getDataInicio(),
                                p.getDataRealTermino()
                        )
                )
                .average()
                .orElse(0.0);
    }

    private Long calcularMembrosUnicosAssociados() {

        return projetoRepository.findAll()
                .stream()
                .flatMap(projeto ->

                        projeto.getMembros() == null
                                ? java.util.stream.Stream.empty()
                                : projeto.getMembros().stream()
                )
                .map(MembroProjeto::getMembroId)
                .distinct()
                .count();
    }
}
