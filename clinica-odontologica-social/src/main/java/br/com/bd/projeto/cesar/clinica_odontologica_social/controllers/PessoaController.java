package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Pessoa;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.PessoaService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    public String inserirPessoa(@RequestBody Pessoa pessoa) {
        service.inserirPessoa(pessoa);
        return "Pessoa inserida!";
    }

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return service.listar();
    }

    @GetMapping("/{cpf}")
    public Pessoa buscarPorCpf(@PathVariable String cpf) {
        return service.buscarPorCpf(cpf);
    }

    @PutMapping("/{cpf}")
    public String atualizar(@PathVariable String cpf, @RequestBody Pessoa pessoa) {
        service.atualizar(cpf, pessoa);
        return "Pessoa atualizada";
    }

    @DeleteMapping("/{cpf}")
    public String deletarPessoa(@PathVariable String cpf) {
        service.deletar(cpf);
        return "Pessoa deletada";
    }
}
