package com.projetos.gestoracademico.controller;

import com.projetos.gestoracademico.model.UnidadeCurricular;
import com.projetos.gestoracademico.service.UnidadeCurricularService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ucs")
public class UnidadeCurricularController {
    private final UnidadeCurricularService unidadeCurricularService;

    public UnidadeCurricularController(UnidadeCurricularService unidadeCurricularService) {
        this.unidadeCurricularService = unidadeCurricularService;
    }

    @GetMapping
    public String listarUcs(Model model){
        List<UnidadeCurricular> ucs = unidadeCurricularService.listAll();

        model.addAttribute("ucs", ucs);
        model.addAttribute("novaUc", new UnidadeCurricular());

        return "ucs";
    }

    @PostMapping
    public String adicionarUc(@ModelAttribute("novaUc") UnidadeCurricular novaUc){
        unidadeCurricularService.saveRgst(novaUc, 0);

        return "redirect:/ucs";
    }

    /*
    @GetMapping("/pesquisar")
    public String pesquisarUc(@RequestParam("nome") String nome, Model model){
        List<UnidadeCurricular> ucsPesquisadas = unidadeCurricularService.listBy(nome);
        List<UnidadeCurricular> ucs = unidadeCurricularService.listAll();

        model.addAttribute("ucs", ucs);
        model.addAttribute("ucsPesquisadas", ucsPesquisadas);
        model.addAttribute("novaUc", new UnidadeCurricular());

        return "ucs";
    }*/
    @GetMapping("/pesquisar")
    public String pesquisarUc(@RequestParam("nome") String nome, Model model){
        System.out.println(">>> PESQUISA: " + nome);

        List<UnidadeCurricular> ucsPesquisadas = unidadeCurricularService.listBy(nome);
        List<UnidadeCurricular> ucs = unidadeCurricularService.listAll();

        model.addAttribute("ucs", ucs);
        model.addAttribute("ucsPesquisadas", ucsPesquisadas);
        model.addAttribute("novaUc", new UnidadeCurricular());

        return "ucs";
    }

    @PostMapping("/remover")
    public String remover(@RequestParam("id_uc") Long id){
        unidadeCurricularService.remove(id);
        return "redirect:/ucs";
    }

    @GetMapping("/atualizar")
    public String atualizarUc(@RequestParam("id_uc") Long id, Model model) {
        Optional<UnidadeCurricular> uc = unidadeCurricularService.getById(id);
        model.addAttribute("uc", uc);
        return "atualizaruc";
    }

    @PostMapping("/atualizar")
    public String atualizarUc(@ModelAttribute("uc") UnidadeCurricular uc) {
        unidadeCurricularService.saveRgst(uc, 1); // save atualiza se o ID existir
        return "redirect:/ucs";
    }
}
