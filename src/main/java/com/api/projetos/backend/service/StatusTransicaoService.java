package com.api.projetos.backend.service;

import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.exception.RegraDeNegocioException;
import com.api.projetos.backend.model.Projeto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.api.projetos.backend.enums.StatusProjeto.*;

@Service
public class StatusTransicaoService {

    private static final Map<StatusProjeto, List<StatusProjeto>> TRANSICOES =
            Map.of(
                    EM_ANALISE,
                    List.of(StatusProjeto.ANALISE_REALIZADA, CANCELADO),

                    ANALISE_REALIZADA,
                    List.of(ANALISE_APROVADA, CANCELADO),

                    ANALISE_APROVADA,
                    List.of(INICIADO, CANCELADO),

                    INICIADO,
                    List.of(PLANEJADO, CANCELADO),

                    PLANEJADO,
                    List.of(EM_ANDAMENTO, CANCELADO),

                    EM_ANDAMENTO,
                    List.of(ENCERRADO, CANCELADO)
            );

    public void validar(
            StatusProjeto atual,
            StatusProjeto proximo
    ) {

        if (proximo == CANCELADO) {
            return;
        }

        if (!TRANSICOES.get(atual).contains(proximo)) {
            throw new RegraDeNegocioException(
                    "Transição de status inválida."
            );
        }
    }

    public void validarExclusao(Projeto projeto) {

        if (
                projeto.getStatusProjeto() == INICIADO ||
                        projeto.getStatusProjeto() == EM_ANDAMENTO ||
                        projeto.getStatusProjeto() == ENCERRADO
        ) {
            throw new RegraDeNegocioException(
                    "Projeto não pode ser removido."
            );
        }

    }
}
