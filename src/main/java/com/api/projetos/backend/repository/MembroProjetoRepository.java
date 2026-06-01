package com.api.projetos.backend.repository;

import com.api.projetos.backend.model.MembroProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembroProjetoRepository extends JpaRepository<MembroProjeto, Long> {
}
