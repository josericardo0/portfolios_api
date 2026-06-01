package com.api.projetos.backend.controller;

import com.api.projetos.backend.dto.*;
import com.api.projetos.backend.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity<ProjetoResponse> criar(
            @Valid @RequestBody ProjetoRequest request
    ) {

        ProjetoResponse response =
                projetoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponse> buscarPorId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                projetoService.buscarPorId(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<ListagemProjetosResponse>> listar(
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                projetoService.listar(pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponse> atualizar(
            @PathVariable UUID id,
            @RequestBody AtualizarProjetoRequest request
    ) {

        return ResponseEntity.ok(
                projetoService.atualizar(id, request)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProjetoResponse> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody AtualizarStatusProjetoRequest request
    ) {

        return ResponseEntity.ok(
                projetoService.atualizarStatus(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable UUID id
    ) {

        projetoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
