package com.api.projetos.backend.controller;


import com.api.projetos.backend.dto.RelatorioResponse;
import com.api.projetos.backend.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/gerar-relatorio")
    public ResponseEntity<RelatorioResponse> gerarRelatorio() {

        return ResponseEntity.ok(
                relatorioService.gerarRelatorio()
        );
    }
}
