package com.api.projetos.backend.dto;

import com.api.projetos.backend.enums.StatusProjeto;

public record AtualizarStatusProjetoRequest(

    StatusProjeto statusProjeto

){}
