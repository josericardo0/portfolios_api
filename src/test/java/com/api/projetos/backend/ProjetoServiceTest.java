package com.api.projetos.backend;

import com.api.projetos.backend.client.MembroClient;
import com.api.projetos.backend.dto.AtualizarStatusProjetoRequest;
import com.api.projetos.backend.dto.MembroResponse;
import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.exception.RecursoNaoEncontradoException;
import com.api.projetos.backend.mapper.ProjetoMapper;
import com.api.projetos.backend.model.Projeto;
import com.api.projetos.backend.repository.ProjetoRepository;
import com.api.projetos.backend.service.CalculoRiscoService;
import com.api.projetos.backend.service.ProjetoService;
import com.api.projetos.backend.service.StatusTransicaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository repository;

    @Mock
    private ProjetoMapper mapper;

    @Mock
    private CalculoRiscoService calculoRiscoService;

    @Mock
    private StatusTransicaoService statusTransicaoService;

    @Mock
    private MembroClient membroClient;

    @InjectMocks
    private ProjetoService service;

    @Test
    void deveLancarErroQuandoProjetoNaoExistir() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(id)
        );
    }

    @Test
    void deveExcluirProjeto() {

        UUID id = UUID.randomUUID();

        Projeto projeto = new Projeto();

        when(repository.findById(id))
                .thenReturn(Optional.of(projeto));

        service.excluir(id);

        verify(repository)
                .delete(projeto);
    }

    @Test
    void deveAtualizarStatusProjeto() {

        UUID id = UUID.randomUUID();

        Projeto projeto = new Projeto();

        projeto.setGerenteId(1L);

        projeto.setStatusProjeto(
                StatusProjeto.EM_ANALISE
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(projeto));

        when(repository.save(any()))
                .thenReturn(projeto);

        when(membroClient.buscar(1L))
                .thenReturn(
                        new MembroResponse(
                                1L,
                                "José",
                                "GERENTE"
                        )
                );

        AtualizarStatusProjetoRequest request =
                new AtualizarStatusProjetoRequest(
                        StatusProjeto.ANALISE_REALIZADA
                );

        service.atualizarStatus(id, request);

        verify(statusTransicaoService)
                .validar(
                        StatusProjeto.EM_ANALISE,
                        StatusProjeto.ANALISE_REALIZADA
                );
    }

}
