package com.api.projetos.backend.service;

import com.api.projetos.backend.dto.MembroRequest;
import com.api.projetos.backend.dto.MembroResponse;
import com.api.projetos.backend.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MembroService {

    private final Map<Long, MembroResponse> banco =
            new ConcurrentHashMap<>();

    private final AtomicLong sequence =
            new AtomicLong(1);

    public MembroResponse criar(
            MembroRequest membroRequest
    ) {

        Long id = sequence.getAndIncrement();

        MembroResponse membro =
                new MembroResponse(
                        id,
                        membroRequest.nome(),
                        membroRequest.funcao()
                );

        banco.put(id, membro);

        return membro;
    }

    public MembroResponse buscar(Long id) {

        MembroResponse membro = banco.get(id);

        if (membro == null) {
            throw new RegraDeNegocioException(
                    "Membro não encontrado."
            );
        }

        return membro;
    }
}
