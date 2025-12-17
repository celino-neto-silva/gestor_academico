package com.projetos.gestoracademico.repository;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {
    List<UnidadeCurricular> findByNomeIgnoreCase(String nome);

    @Override
    Optional<UnidadeCurricular> findById(Long id);

}
