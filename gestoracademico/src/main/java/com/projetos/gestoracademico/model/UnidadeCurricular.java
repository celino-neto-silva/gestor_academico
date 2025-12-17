package com.projetos.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeCurricular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_uc;
    private String nome;
    private int duracao;

    @ManyToMany(mappedBy = "ucs")
    private List<Curso> cursos;

    @ManyToOne
    @JoinColumn(name = "edicao_id")
    private Edicao edicao;
}
