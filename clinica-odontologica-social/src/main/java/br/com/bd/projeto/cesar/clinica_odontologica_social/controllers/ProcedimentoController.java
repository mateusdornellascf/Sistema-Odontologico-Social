package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Procedimento;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.ProcedimentoService;

@RestController
@RequestMapping("/consultas/{idConsulta}/procedimentos")
public class ProcedimentoController {
    private final ProcedimentoService procedimentoService;

    public ProcedimentoController(ProcedimentoService procedimentoService) {
        this.procedimentoService = procedimentoService;
    }

    @PostMapping
    public String criarProcedimento(){
        return procedimentoService.criarProcedimento();
    }

    @GetMapping
    public List<Procedimento> listar(){
        return procedimentoService.listar();
    }
    @PutMapping
    public List<Procedimento> atualizar(){
        return procedimentoService.atualizar();
    }

    @DeleteMapping
    public String deletar(){
        procedimentoService.deletar();
        return "Procedimento deletado com sucesso!";
    }
}
