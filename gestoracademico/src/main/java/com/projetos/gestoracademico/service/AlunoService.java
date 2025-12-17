package com.projetos.gestoracademico.service;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Matricula;
import com.projetos.gestoracademico.repository.AlunoRepository;
import com.projetos.gestoracademico.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public AlunoService(AlunoRepository alunoRepository, MatriculaRepository matriculaRepository){
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;
    }
    //1º Listagem
    public List<Aluno> listAll(){
        return alunoRepository.findAll();
    }

    //2º Listagem especifica - Aplicando a regra de negócio de pesquisar com case insensitive
    public List<Aluno> listBy(String nome){
        return alunoRepository.findByNomeIgnoreCase(nome);
    }

    //3º Cadastro - Aplicando a regra de negócio de não cadastrar aluno com mesmo email porem respeita se é uma atualização
    public Aluno saveRgst(Aluno aluno, int updt){
        //Inserção: Não pode haver email repetido, Atualização: Só pode haver o email do aluno a ser inserido é tem que fazer ignore case
        if (updt == 0){
            int existed = 0;
            for (Aluno a : alunoRepository.findAll()){
                if(a.getEmail().equalsIgnoreCase(aluno.getEmail())){
                    existed = 1;
                    break;
                }
            }

            if (existed == 0){
                return alunoRepository.save(aluno);
            }
            else {
                return null;
            }
        }
        else {
            int existed = 0;

            for (Aluno a : alunoRepository.findAll()){
                if (a.getEmail().equalsIgnoreCase(aluno.getEmail()) && !Objects.equals(a.getId_aluno(), aluno.getId_aluno())){
                    existed = 1;
                    break;
                }
            }
            if (existed == 0){
                return alunoRepository.save(aluno);
            }
            else {
                return null;
            }
        }
    }

    //4º Atualização de dados do aluno
    public Optional<Aluno> getById(Long id){
        return alunoRepository.findById(id);
    }

    //5º Remover aplicando a regra de negocio de não remover uma entidade ligada a outro lugar
    public void remove(Long id){
        Aluno aluno_remocao = new Aluno();
        for (Aluno a : alunoRepository.findAll()){
            if(Objects.equals(a.getId_aluno(), id)){
                aluno_remocao = a;
                break;
            }
        }
        int existed = 0;
        for (Matricula m : matriculaRepository.findAll()){
            if (m.getAluno()==aluno_remocao){
                existed = 1;
                break;
            }
        }
        if (existed == 0){
            alunoRepository.deleteById(id);
        }
    }


}
