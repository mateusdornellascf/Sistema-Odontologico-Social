package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Paciente;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.PacienteService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }
    @PostMapping
    public String inserirPaciente(@RequestBody Paciente paciente) {
        service.inserirPaciente(paciente);
        return "Paciente inserida!";
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return service.listar();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> buscarPorCpf(@PathVariable String cpf) {
        Paciente p = service.buscarPorCpf(cpf);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{cpf}")
    public String atualizar(@PathVariable String cpf, @RequestBody Paciente paciente) {
        service.atualizar(cpf, paciente);
        return "Paciente atualizada";
    }

    @DeleteMapping("/{cpf}")
    public String deletarPaciente(@PathVariable String cpf) {
        service.deletar(cpf);
        return "Paciente deletada";
    }
}
