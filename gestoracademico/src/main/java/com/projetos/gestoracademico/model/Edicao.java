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
public class Edicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_edicao;

    private String nome;
    private String data_inicio;
    private String data_fim;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @ManyToMany
    @JoinTable(
            name = "edicao_uc",
            joinColumns = @JoinColumn(name = "id_edicao"),
            inverseJoinColumns = @JoinColumn(name = "id_uc")
    )
    private List<UnidadeCurricular> ucs = new ArrayList<>();

    @Transient
    private List<Long> ucsIds;

    @Transient
    public int getDuracaoTotal() {
        if (ucs == null) {
            return 0;
        }
        return ucs.stream()
                .mapToInt(UnidadeCurricular::getDuracao)
                .sum();
    }

    @ManyToOne
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;
}
