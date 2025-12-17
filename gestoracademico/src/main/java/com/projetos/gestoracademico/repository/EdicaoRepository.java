package com.projetos.gestoracademico.repository;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Edicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EdicaoRepository extends JpaRepository<Edicao, Long> {
    List<Edicao> findByNomeIgnoreCase(String nome);

    @Override
    Optional<Edicao> findById(Long id);
}
