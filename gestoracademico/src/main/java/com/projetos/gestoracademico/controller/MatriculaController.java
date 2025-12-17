package com.projetos.gestoracademico.controller;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.model.Edicao;
import com.projetos.gestoracademico.model.Matricula;
import com.projetos.gestoracademico.service.AlunoService;
import com.projetos.gestoracademico.service.EdicaoService;
import com.projetos.gestoracademico.service.MatriculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {
    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final EdicaoService edicaoService;

    public MatriculaController(MatriculaService matriculaService, AlunoService alunoService, EdicaoService edicaoService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.edicaoService = edicaoService;
    }

    @GetMapping
    public String listarMatriculas(Model model){
        List<Matricula> matriculas = matriculaService.listAll();
        List<Aluno> alunos = alunoService.listAll();
        List<Edicao> edicoes = edicaoService.listAll();

        model.addAttribute("matriculas", matriculas);
        model.addAttribute("alunos", alunos);
        model.addAttribute("edicoes", edicoes);
        model.addAttribute("novaMatricula", new Matricula());

        return "matriculas";
    }

    @GetMapping("/pesquisar")
    public String pesquisarMatricula(@RequestParam("id_edicao") Long id, Model model){
        model.addAttribute("matriculas", matriculaService.listAll());
        model.addAttribute("matriculasPesquisadas", matriculaService.listByEdition(id));
        model.addAttribute("alunos", alunoService.listAll());
        model.addAttribute("edicoes", edicaoService.listAll());
        model.addAttribute("novaMatricula", new Matricula());
        return "matriculas";
    }

    @PostMapping
    public String adicionarMatricula(@ModelAttribute("novaMatricula") Matricula novaMatricula){
        matriculaService.saveRgst(novaMatricula);
        return "redirect:/matriculas";
    }

    @PostMapping("/remover")
    public String removerMatricula(@RequestParam("id_matricula") Long id){
        matriculaService.remove(id);
        return "redirect:/matriculas";
    }
}
