package com.projetos.gestoracademico.controller;

import com.projetos.gestoracademico.model.Curso;
import com.projetos.gestoracademico.model.Edicao;
import com.projetos.gestoracademico.model.UnidadeCurricular;
import com.projetos.gestoracademico.service.CursoService;
import com.projetos.gestoracademico.service.EdicaoService;
import com.projetos.gestoracademico.service.UnidadeCurricularService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService cursoService;
    private final EdicaoService edicaoService;

    public CursoController(CursoService cursoService, EdicaoService edicaoService) {
        this.cursoService = cursoService;
        this.edicaoService = edicaoService;
    }

    @GetMapping
    public String listarCursos(Model model){
        List<Curso> cursos = cursoService.listAll();

        model.addAttribute("cursos", cursos);
        model.addAttribute("novoCurso", new Curso());
        return "cursos";
    }

    @PostMapping
    public String adicionarCurso(@ModelAttribute("novoCurso") Curso novoCurso){
        cursoService.saveRgst(novoCurso, 0);
        return "redirect:/cursos";
    }

    @PostMapping("/remover")
    public String removerCurso(@RequestParam("id_curso") Long id){
        cursoService.remove(id);
        return "redirect:/cursos";
    }

    @GetMapping("/pesquisar")
    public String pesquisarCurso(@RequestParam("nome") String nome, Model model){
        List<Curso> cursosPesquisados = cursoService.listBy(nome);
        List<Curso> todosCursos = cursoService.listAll();

        model.addAttribute("cursos", todosCursos);
        model.addAttribute("cursosPesquisados", cursosPesquisados);
        model.addAttribute("novoCurso", new Curso());

        return "cursos";
    }

    @GetMapping("/atualizar")
    public String atualizarCurso(@RequestParam("id_curso") Long id, Model model) {
        Optional<Curso> curso = cursoService.getById(id);
        model.addAttribute("curso", curso);
        return "atualizarcurso";
    }

    @PostMapping("/atualizar")
    public String atualizarCurso(@ModelAttribute("curso") Curso curso) {
        cursoService.saveRgst(curso, 1);
        return "redirect:/cursos";
    }
}


