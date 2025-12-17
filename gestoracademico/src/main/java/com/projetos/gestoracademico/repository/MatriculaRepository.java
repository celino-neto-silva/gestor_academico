package com.projetos.gestoracademico.repository;

import com.projetos.gestoracademico.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    @Query("SELECT m FROM Matricula m WHERE m.edicao.id_edicao = :id")
    List<Matricula> findByEditionId(@Param("id") Long id);

    @Override
    Optional<Matricula> findById(Long id);
}
