package com.projetos.gestoracademico.controller;

import com.projetos.gestoracademico.model.Aluno;
import com.projetos.gestoracademico.service.AlunoService;
import com.projetos.gestoracademico.service.MatriculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AlunoController {
    private final AlunoService alunoService;
    private final MatriculaService matriculaService;

    public AlunoController(AlunoService alunoService, MatriculaService matriculaService) {
        this.alunoService = alunoService;
        this.matriculaService = matriculaService;
    }

    @GetMapping("/")
    public String paginaInicial(){
        return "redirect:/alunos";
    }

    @GetMapping("/alunos")
    public String listarAlunos(Model model){
        List<Aluno> alunos = alunoService.listAll();
        model.addAttribute("alunos", alunos);
        model.addAttribute("novoAluno", new Aluno());
        return "alunos";
    }

    @PostMapping("/alunos")
    public String adicionarAluno(@ModelAttribute("novoAluno") Aluno novoAluno){
        alunoService.saveRgst(novoAluno, 0);
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/pesquisar")
    public String pesquisarAluno(@RequestParam("nome") String nome, Model model){
        List<Aluno> alunosPesquisados = alunoService.listBy(nome);
        List<Aluno> todosAlunos = alunoService.listAll();

        model.addAttribute("alunos", todosAlunos);
        model.addAttribute("alunosPesquisados", alunosPesquisados);
        model.addAttribute("novoAluno", new Aluno());

        return "alunos";
    }

    @PostMapping("/alunos/remover")
    public String removerAluno(@RequestParam("id_aluno") Long id){
        alunoService.remove(id);
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/atualizar")
    public String atualizarAluno(@RequestParam("id_aluno") Long id, Model model) {
        Optional<Aluno> aluno = alunoService.getById(id);
        model.addAttribute("aluno", aluno);
        return "atualizaraluno";
    }

    @PostMapping("/alunos/atualizar")
    public String atualizarAluno(@ModelAttribute("aluno") Aluno aluno) {
        alunoService.saveRgst(aluno, 1); // save atualiza se o ID existir
        return "redirect:/alunos";
    }
}
