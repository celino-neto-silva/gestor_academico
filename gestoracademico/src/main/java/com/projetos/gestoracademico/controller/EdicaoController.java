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
@RequestMapping("/edicoes")
public class EdicaoController {
    private final EdicaoService edicaoService;
    private final CursoService cursoService;
    private final UnidadeCurricularService unidadeCurricularService;

    public EdicaoController(EdicaoService edicaoService, CursoService cursoService, UnidadeCurricularService unidadeCurricularService) {
        this.edicaoService = edicaoService;
        this.cursoService = cursoService;
        this.unidadeCurricularService = unidadeCurricularService;
    }

    @GetMapping
    public String listarEdicoes(Model model){
        List<Edicao> edicoes = edicaoService.listAll();
        List<Curso> cursos = cursoService.listAll();
        List<UnidadeCurricular> ucs = unidadeCurricularService.listAll();

        model.addAttribute("edicoes", edicoes);
        model.addAttribute("ucs", ucs);
        model.addAttribute("cursos", cursos);
        model.addAttribute("novaEdicao", new Edicao());
        return "edicoes";
    }

    @PostMapping
    public String adicionarEdicao(@ModelAttribute("novaEdicao") Edicao novaEdicao){
        edicaoService.saveRgst(novaEdicao, 0);
        return "redirect:/edicoes";
    }

    @GetMapping("/pesquisar")
    public String pesquisarEdicao(@RequestParam("nome") String nome, Model model){

        model.addAttribute("edicoes", edicaoService.listAll());
        model.addAttribute("edicoesPesquisadas", edicaoService.listBy(nome));
        model.addAttribute("cursos", cursoService.listAll());
        model.addAttribute("ucs", unidadeCurricularService.listAll());
        model.addAttribute("novaEdicao", new Edicao());
        return "edicoes";
    }
    /*
    @GetMapping("/atualizar")
    public String atualizarEdicao(@RequestParam("id_edicao") Long id, Model model){
        Optional<Edicao> edicao = edicaoService.getById(id);
        model.addAttribute("edicao", edicao);
        return "atualizaredicao";
    }
    */

    @GetMapping("/atualizar")
    public String atualizarEdicao(@RequestParam("id_edicao") Long id, Model model){
        Edicao edicao = edicaoService.getById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrada"));

        model.addAttribute("edicao", edicao);
        model.addAttribute("cursos", cursoService.listAll());
        model.addAttribute("ucs", unidadeCurricularService.listAll());

        return "atualizaredicao";
    }

    @PostMapping("/atualizar")
    public String atualizarEdicao(@ModelAttribute("edicao") Edicao edicao){
        edicaoService.saveRgst(edicao, 1);
        return "redirect:/edicoes";
    }

    @PostMapping("/remover")
    public String removerEdicao(@RequestParam("id_edicao") Long id){
        edicaoService.remove(id);
        return "redirect:/edicoes";
    }
}
