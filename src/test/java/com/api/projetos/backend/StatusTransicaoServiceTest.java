package com.api.projetos.backend;

import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.exception.RegraDeNegocioException;
import com.api.projetos.backend.model.Projeto;
import com.api.projetos.backend.service.StatusTransicaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StatusTransicaoServiceTest {

    private final StatusTransicaoService service =
            new StatusTransicaoService();

    @Test
    void devePermitirTransicaoValida() {

        assertDoesNotThrow(() ->
                service.validar(
                        StatusProjeto.EM_ANALISE,
                        StatusProjeto.ANALISE_REALIZADA
                )
        );
    }

    @Test
    void deveLancarErroParaTransicaoInvalida() {

        assertThrows(
                RegraDeNegocioException.class,
                () -> service.validar(
                        StatusProjeto.EM_ANALISE,
                        StatusProjeto.INICIADO
                )
        );
    }

    @Test
    void devePermitirCancelamento() {

        assertDoesNotThrow(() ->
                service.validar(
                        StatusProjeto.EM_ANALISE,
                        StatusProjeto.CANCELADO
                )
        );
    }

    @Test
    void naoDevePermitirExcluirProjetoIniciado() {

        Projeto projeto = new Projeto();

        projeto.setStatusProjeto(
                StatusProjeto.INICIADO
        );

        assertThrows(
                RegraDeNegocioException.class,
                () -> service.validarExclusao(
                        projeto
                )
        );
    }
}
