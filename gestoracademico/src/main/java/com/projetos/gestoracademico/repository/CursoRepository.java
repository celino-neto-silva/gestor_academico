package com.projetos.gestoracademico.repository;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByNomeIgnoreCase(String nome);

    @Override
    Optional<Curso> findById(Long id);
}
