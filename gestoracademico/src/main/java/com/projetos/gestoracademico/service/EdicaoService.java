package com.projetos.gestoracademico.service;

import com.projetos.gestoracademico.model.Curso;
import com.projetos.gestoracademico.model.Edicao;
import com.projetos.gestoracademico.model.Matricula;
import com.projetos.gestoracademico.model.UnidadeCurricular;
import com.projetos.gestoracademico.repository.CursoRepository;
import com.projetos.gestoracademico.repository.EdicaoRepository;
import com.projetos.gestoracademico.repository.MatriculaRepository;
import com.projetos.gestoracademico.repository.UnidadeCurricularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EdicaoService {
    private final EdicaoRepository edicaoRepository;
    private final MatriculaRepository matriculaRepository;
    private final UnidadeCurricularRepository unidadeCurricularRepository;
    private final CursoRepository cursoRepository;

    public EdicaoService(EdicaoRepository edicaoRepository, MatriculaRepository matriculaRepository, UnidadeCurricularRepository unidadeCurricularRepository, CursoRepository cursoRepository) {
        this.edicaoRepository = edicaoRepository;
        this.matriculaRepository = matriculaRepository;
        this.unidadeCurricularRepository = unidadeCurricularRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Edicao> listAll(){
        return edicaoRepository.findAll();
    }

    public List<Edicao> listBy(String nome){
        return edicaoRepository.findByNomeIgnoreCase(nome);
    }

    public Optional<Edicao> getById(Long id){
        return edicaoRepository.findById(id);
    }

    public Edicao saveRgst(Edicao edicao, int updt) {
        //return edicaoRepository.save(edicao);
        if (updt == 0) {
            int check = 0;
            for (Edicao e : edicaoRepository.findAll()) {
                if (e.getNome().equalsIgnoreCase(edicao.getNome())) {
                    check = 1;
                    break;
                }
            }

            if (edicao.getData_fim().compareTo(edicao.getData_inicio()) == 0 || edicao.getData_fim().compareTo(edicao.getData_inicio()) < 0){
                check = 1;
            }

            if (check == 0) {
                if (edicao.getUcsIds() != null && !edicao.getUcsIds().isEmpty()) {
                    List<UnidadeCurricular> ucs =
                            unidadeCurricularRepository.findAllById(edicao.getUcsIds());
                    edicao.setUcs(ucs);
                }
                return edicaoRepository.save(edicao);
            } else {
                return null;
            }
        }
        else {
            int check = 0;
            for (Edicao e : edicaoRepository.findAll()) {
                if (e.getNome().equalsIgnoreCase(edicao.getNome()) && !Objects.equals(e.getId_edicao(), edicao.getId_edicao())) {
                    check = 1;
                    break;
                }
            }

            if (edicao.getData_fim().compareTo(edicao.getData_inicio()) == 0 || edicao.getData_fim().compareTo(edicao.getData_inicio()) < 0){
                check = 1;
            }

            if (check == 0) {
                if (edicao.getUcsIds() != null && !edicao.getUcsIds().isEmpty()) {
                    List<UnidadeCurricular> ucs =
                            unidadeCurricularRepository.findAllById(edicao.getUcsIds());
                    edicao.setUcs(ucs);
                }
                return edicaoRepository.save(edicao);
            }
            else {
                return null;
            }
        }
    }

    public void remove(Long id){
        Edicao edicao_remocao = new Edicao();
        for (Edicao e : edicaoRepository.findAll()){
            if(Objects.equals(e.getId_edicao(), id)){
                edicao_remocao = e;
                break;
            }
        }
        int existed = 0;
        for (Matricula m : matriculaRepository.findAll()){
            if (m.getEdicao()==edicao_remocao){
                existed = 1;
                break;
            }
        }
        if (existed == 0){
            edicaoRepository.deleteById(id);
        }
    }
}
