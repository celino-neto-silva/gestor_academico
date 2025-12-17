package com.projetos.gestoracademico.service;

import com.projetos.gestoracademico.model.*;
import com.projetos.gestoracademico.repository.CursoRepository;
import com.projetos.gestoracademico.repository.EdicaoRepository;
import com.projetos.gestoracademico.repository.UnidadeCurricularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnidadeCurricularService {
    private final UnidadeCurricularRepository unidadeCurricularRepository;
    private final CursoRepository cursoRepository;
    private final EdicaoRepository edicaoRepository;

    public UnidadeCurricularService(UnidadeCurricularRepository unidadeCurricularRepository, CursoRepository cursoRepository, EdicaoRepository edicaoRepository) {
        this.unidadeCurricularRepository = unidadeCurricularRepository;
        this.cursoRepository = cursoRepository;
        this.edicaoRepository = edicaoRepository;
    }

    public List<UnidadeCurricular> listAll(){
        return unidadeCurricularRepository.findAll();
    }

    public List<UnidadeCurricular> listBy(String nome){
        return unidadeCurricularRepository.findByNomeIgnoreCase(nome);
    }

    public Optional<UnidadeCurricular> getById(Long id){
        return unidadeCurricularRepository.findById(id);
    }

    public UnidadeCurricular saveRgst(UnidadeCurricular unidadeCurricular, int updt){
        if (updt == 0){
            int existed = 0;
            for (UnidadeCurricular u : unidadeCurricularRepository.findAll()){
                if (u.getNome().equalsIgnoreCase(unidadeCurricular.getNome())){
                    existed = 1;
                    break;
                }
            }

            if (existed == 0){
                return unidadeCurricularRepository.save(unidadeCurricular);
            }
            else{
                return null;
            }
        }
        else {
            int existed = 0;

            for (UnidadeCurricular u : unidadeCurricularRepository.findAll()){
                if(u.getNome().equalsIgnoreCase(unidadeCurricular.getNome()) && !Objects.equals(u.getId_uc(), unidadeCurricular.getId_uc())){
                    existed = 1;
                    break;
                }
            }
            if (existed == 0){
                return unidadeCurricularRepository.save(unidadeCurricular);
            }
            else {
                return null;
            }
        }
    }

    public void remove(Long id){
        UnidadeCurricular uc_remocao = new UnidadeCurricular();
        for (UnidadeCurricular u : unidadeCurricularRepository.findAll()){
            if(Objects.equals(u.getId_uc(), id)){
                uc_remocao = u;
                break;
            }
        }

        int existed = 0;
        for (Edicao e : edicaoRepository.findAll()){
            if (e.getUcs().contains(uc_remocao)){
                existed = 1;
                break;
            }
        }

        if (existed == 0){
            unidadeCurricularRepository.deleteById(id);
        }
    }
}
