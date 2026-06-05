package com.api.projetos.backend.controller;

import com.api.projetos.backend.dto.MembroRequest;
import com.api.projetos.backend.dto.MembroResponse;
import com.api.projetos.backend.service.MembroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock-api/membros")
public class MembroProjetoController {

    private final MembroService membroService;

    public MembroProjetoController(
            MembroService membroService
    ) {
        this.membroService = membroService;
    }

    @PostMapping("/criar-membro")
    public ResponseEntity<MembroResponse> criar(
            @RequestBody MembroRequest membroRequest
    ) {
        return ResponseEntity.ok(
                membroService.criar(membroRequest)
        );
    }

    @GetMapping("/buscar-membro/{id}")
    public ResponseEntity<MembroResponse> buscar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                membroService.buscar(id)
        );
    }

}
