package com.api.projetos.backend.repository;


import com.api.projetos.backend.enums.StatusProjeto;
import com.api.projetos.backend.model.Projeto;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {

    @Query("""
        SELECT p.statusProjeto, COUNT(p)
        FROM Projeto p
        GROUP BY p.statusProjeto
    """)
    List<Object[]> countProjetosPorStatus();

    @Query("""
        SELECT p.statusProjeto, COALESCE(SUM(p.orcamentoTotal), 0)
        FROM Projeto p
        GROUP BY p.statusProjeto
    """)
    List<Object[]> sumOrcamentoPorStatus();

    @Query("""
        SELECT COUNT(p)
        FROM Projeto p
        JOIN p.membros m
        WHERE m.membroId = :membroId
        AND p.statusProjeto NOT IN (
            com.api.projetos.backend.enums.StatusProjeto.ENCERRADO,
            com.api.projetos.backend.enums.StatusProjeto.CANCELADO
        )
    """)
    long contarProjetosAtivosPorMembro(
            @Param("membroId") Long membroId
    );

    List<Projeto> findByStatusProjeto(
            StatusProjeto statusProjeto
    );
}
