package com.projetos.gestoracademico.service;
import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Curso;
import com.projetos.gestoracademico.model.Edicao;
import com.projetos.gestoracademico.model.UnidadeCurricular;
import com.projetos.gestoracademico.repository.CursoRepository;
import com.projetos.gestoracademico.repository.EdicaoRepository;
import com.projetos.gestoracademico.repository.UnidadeCurricularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CursoService {
    public final CursoRepository cursoRepository;
    public final UnidadeCurricularRepository unidadeCurricularRepository;
    private final EdicaoRepository edicaoRepository;

    public CursoService(CursoRepository cursoRepository, UnidadeCurricularRepository unidadeCurricularRepository, EdicaoRepository edicaoRepository) {
        this.cursoRepository = cursoRepository;
        this.unidadeCurricularRepository = unidadeCurricularRepository;
        this.edicaoRepository = edicaoRepository;
    }
    //1º Listagem
    public List<Curso> listAll(){
        return cursoRepository.findAll();
    }

    //2º Listagem especifica
    public List<Curso> listBy(String nome){
        return cursoRepository.findByNomeIgnoreCase(nome);
    }

    //3º Adicionar curso - aplicando a regra de negócio de não adicionar curso repetido
    public Curso saveRgst(Curso curso, int updt){
        if(updt == 0){
            int existed = 0;
            for (Curso c : cursoRepository.findAll()){
                if (c.getNome().equalsIgnoreCase(curso.getNome())){
                    existed = 1;
                    break;
                }
            }

            for (UnidadeCurricular u : unidadeCurricularRepository.findAll()){
                if (Objects.equals(u.getNome(), curso.getNome())){
                    existed = 1;
                    break;
                }
            }

            if (existed == 0){
                return cursoRepository.save(curso);
            }
            else {
                return null;
            }
        }
        else{
            int existed = 0;
            for (Curso c : cursoRepository.findAll()){
                if (c.getNome().equalsIgnoreCase(curso.getNome()) && !Objects.equals(c.getId_curso(), curso.getId_curso())){
                    existed = 1;
                    break;
                }
            }
            if (existed == 0){
                return cursoRepository.save(curso);
            }
            else {
                return null;
            }
        }
    }

    //4º Remover aplicando a regra de negocio de não remover uma entidade ligada a outro lugar
    public void remove(Long id){
        Curso curso_remocao = new Curso();
        for (Curso c : cursoRepository.findAll()){
            if (Objects.equals(c.getId_curso(), id)){
                curso_remocao = c;
                break;
            }
        }

        int existed = 0;
        for (Edicao e : edicaoRepository.findAll()){
            if (e.getCurso() == curso_remocao){
                existed = 1;
                break;
            }
        }

        if (existed == 0){
            cursoRepository.deleteById(id);
        }
    }

    public List<UnidadeCurricular> listAllUcs(){
        return unidadeCurricularRepository.findAll();
    }

    public Optional<Curso> getById(Long id){
        return cursoRepository.findById(id);
    }
}
