package com.api.projetos.backend.service;

import com.api.projetos.backend.client.MembroClient;
import com.api.projetos.backend.dto.*;
import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.exception.RecursoNaoEncontradoException;
import com.api.projetos.backend.exception.RegraDeNegocioException;
import com.api.projetos.backend.mapper.ProjetoMapper;
import com.api.projetos.backend.model.MembroProjeto;
import com.api.projetos.backend.model.Projeto;
import com.api.projetos.backend.repository.ProjetoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;
    private final CalculoRiscoService calculoRiscoService;
    private final StatusTransicaoService statusTransicaoService;
    private final MembroClient membroClient;


    public ProjetoService(ProjetoRepository projetoRepository, ProjetoMapper projetoMapper, CalculoRiscoService calculoRiscoService, StatusTransicaoService statusTransicaoService, MembroClient membroClient) {
        this.projetoRepository = projetoRepository;
        this.projetoMapper = projetoMapper;
        this.calculoRiscoService = calculoRiscoService;
        this.statusTransicaoService = statusTransicaoService;
        this.membroClient = membroClient;
    }

    public ProjetoResponse criar(
            ProjetoRequest request
    ) {

        validarMembros(request);

        Projeto projeto =
                projetoMapper.toEntity(request);

        projeto.setStatusProjeto(
                StatusProjeto.EM_ANALISE
        );

        List<MembroProjeto> membros =
                request.membrosIds()
                        .stream()
                        .map(id -> {

                            MembroResponse response =
                                    membroClient.buscar(id);

                            MembroProjeto membro =
                                    new MembroProjeto();

                            membro.setMembroId(
                                    response.membroId()
                            );

                            membro.setNome(
                                    response.nome()
                            );

                            membro.setFuncao(
                                    response.funcao()
                            );

                            return membro;
                        })
                        .toList();

        projeto.setMembros(membros);

        projeto = projetoRepository.save(projeto);

        return projetoMapper.toResponse(
                projeto,
                "Gerente",
                calculoRiscoService.calculate(projeto)
        );
    }

    @Transactional(readOnly = true)
    public ProjetoResponse buscarPorId(UUID id) {

        Projeto projeto = obterProjeto(id);

        return projetoMapper.toResponse(
                projeto,
                "Gerente",
                calculoRiscoService.calculate(projeto)
        );
    }

    private void validarMembros(
            ProjetoRequest request
    ) {

        if (request.membrosIds() == null ||
                request.membrosIds().isEmpty()) {

            throw new RegraDeNegocioException(
                    "O projeto deve possuir ao menos um membro."
            );
        }

        for (Long membroId : request.membrosIds()) {

            MembroResponse membro =
                    membroClient.buscar(membroId);

            if (!"FUNCIONARIO".equalsIgnoreCase(
                    membro.funcao()
            )) {

                throw new RegraDeNegocioException(
                        "Somente funcionários podem ser associados ao projeto."
                );
            }

            long quantidade =
                    projetoRepository
                            .contarProjetosAtivosPorMembro(
                                    membroId
                            );

            if (quantidade >= 3) {

                throw new RegraDeNegocioException(
                        "Membro já está alocado em 3 projetos ativos."
                );
            }
        }
    }

    public ProjetoResponse atualizar(
            UUID id,
            AtualizarProjetoRequest request
    ) {

        Projeto projeto = obterProjeto(id);

        if (request.nome() != null) {
            projeto.setNome(request.nome());
        }

        if (request.descricao() != null) {
            projeto.setDescricao(request.descricao());
        }

        if (request.orcamentoTotal() != null) {
            projeto.setOrcamentoTotal(
                    request.orcamentoTotal()
            );
        }

        if (request.dataTerminoPrevisto() != null) {
            projeto.setDataTerminoPrevisto(
                    request.dataTerminoPrevisto()
            );
        }

        if (request.dataRealTermino() != null) {
            projeto.setDataRealTermino(
                    request.dataRealTermino()
            );
        }

        projeto = projetoRepository.save(projeto);

        return projetoMapper.toResponse(
                projeto,
                "Gerente",
                calculoRiscoService.calculate(projeto)
        );
    }

    public ProjetoResponse atualizarStatus(
            UUID id,
            AtualizarStatusProjetoRequest request
    ) {

        Projeto projeto = obterProjeto(id);

        statusTransicaoService.validar(
                projeto.getStatusProjeto(),
                request.statusProjeto()
        );

        projeto.setStatusProjeto(
                request.statusProjeto()
        );

        projeto = projetoRepository.save(projeto);

        return projetoMapper.toResponse(
                projeto,
                "Gerente",
                calculoRiscoService.calculate(projeto)
        );
    }

    public void excluir(UUID id) {

        Projeto projeto = obterProjeto(id);

        statusTransicaoService.validarExclusao(
                projeto
        );

        projetoRepository.delete(projeto);
    }

    @Transactional(readOnly = true)
    public Page<ListagemProjetosResponse> listar(
            Pageable pageable
    ) {

        return projetoRepository
                .findAll(pageable)
                .map(projetoMapper::toSummary);
    }

    private Projeto obterProjeto(UUID id) {

        return projetoRepository
                .findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Projeto não encontrado."
                        ));
    }
}
