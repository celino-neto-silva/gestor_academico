package com.projetos.gestoracademico.service;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Matricula;
import com.projetos.gestoracademico.repository.AlunoRepository;
import com.projetos.gestoracademico.repository.EdicaoRepository;
import com.projetos.gestoracademico.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {
    public final MatriculaRepository matriculaRepository;
    public final AlunoRepository alunoRepository;
    public final EdicaoRepository edicaoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, EdicaoRepository edicaoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.edicaoRepository = edicaoRepository;
    }

    public List<Matricula> listAll(){
        return matriculaRepository.findAll();
    }

    public List<Matricula> listByEdition(Long id){
        return matriculaRepository.findByEditionId(id);
    }

    public Matricula saveRgst(Matricula matricula){
        return matriculaRepository.save(matricula);
    }

    public void remove(Long id){
        matriculaRepository.deleteById(id);
    }

}
