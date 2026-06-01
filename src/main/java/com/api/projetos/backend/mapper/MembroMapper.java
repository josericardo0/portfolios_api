package com.api.projetos.backend.mapper;

import com.api.projetos.backend.dto.MembroResponse;
import com.api.projetos.backend.model.MembroProjeto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembroMapper {

    MembroProjeto toEntity(MembroResponse response);

    MembroResponse toResponse(MembroProjeto membro);

}
