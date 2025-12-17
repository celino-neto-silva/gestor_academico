package com.projetos.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_curso;
    private String nome;
    private int nivel;

    @ManyToMany
    @JoinTable(
            name = "curso_unidadecurricular",
            joinColumns = @JoinColumn(name = "id_curso"),
            inverseJoinColumns = @JoinColumn(name = "id_uc")
    )
    private List<UnidadeCurricular> ucs;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Edicao> edicoes = new ArrayList<>();
}
