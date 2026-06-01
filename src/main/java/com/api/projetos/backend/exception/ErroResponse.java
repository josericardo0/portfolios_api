package com.api.projetos.backend.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        Integer status,
        String message,
        LocalDateTime timestamp
) {

    public ErroResponse(Integer status, String message) {
        this(
                status,
                message,
                LocalDateTime.now()
        );
    }
}