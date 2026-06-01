package com.api.projetos.backend.client;

import com.api.projetos.backend.dto.MembroResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "membro-api",
        url = "${membro.api.url}"
)
public interface MembroClient {

    @GetMapping("/mock-api/membros/{id}")
    MembroResponse buscar(
            @PathVariable Long id
    );
}
