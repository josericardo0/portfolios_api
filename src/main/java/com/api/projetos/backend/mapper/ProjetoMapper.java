package com.api.projetos.backend.mapper;

import com.api.projetos.backend.dto.ListagemProjetosResponse;
import com.api.projetos.backend.dto.ProjetoRequest;
import com.api.projetos.backend.dto.ProjetoResponse;
import com.api.projetos.backend.enums.NivelRiscoProjeto;
import com.api.projetos.backend.model.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = MembroMapper.class
)
public interface ProjetoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statusProjeto", ignore = true)
    @Mapping(target = "membros", ignore = true)
    @Mapping(target = "dataRealTermino", ignore = true)
    Projeto toEntity(ProjetoRequest request);

    @Mapping(target = "nomeGerente", source = "nomeGerente")
    @Mapping(target = "nivelRiscoProjeto", source = "nivelRiscoProjeto")
    ProjetoResponse toResponse(
            Projeto projeto,
            String nomeGerente,
            NivelRiscoProjeto nivelRiscoProjeto
    );

    @Mapping(target = "nivelRiscoProjeto", ignore = true)
    ListagemProjetosResponse toSummary(Projeto projeto);
}