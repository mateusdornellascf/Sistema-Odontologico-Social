package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.FormularioSaude;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Paciente;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.FormularioSaudeService;
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
    private final FormularioSaudeService formularioSaudeService;

    public PacienteController(PacienteService service, FormularioSaudeService formularioSaudeService) {
        this.service = service;
        this.formularioSaudeService = formularioSaudeService;
    }

    @PostMapping
    public String inserirPaciente(@RequestBody Paciente paciente) {
        service.inserirPaciente(paciente);
        return "Paciente inserida!";
    }

    @GetMapping("/listar")
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

    @PostMapping("/{cpf}/formulario-saude")
    public ResponseEntity<String> preencherFormularioSaude(
            @PathVariable String cpf,
            @RequestBody FormularioSaude formulario) {

        formularioSaudeService.salvarOuAtualizar(
                cpf,
                formulario.getAlergias(),
                formulario.getDoencas(),
                formulario.getMedicamentos());

        return ResponseEntity.ok("Formulário salvo/atualizado com sucesso!");
    }
    @GetMapping("/sem-consulta")
    public ResponseEntity<?> getPacientesSemConsulta() {
        List<Paciente> resultado = service.buscarPacientesSemConsulta();
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("Todos os pacientes cadastrados já possuem ao menos uma consulta.");
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<Paciente> resultado = service.buscarPorNome(nome);
            if (resultado.isEmpty()) {
                return ResponseEntity.ok("Nenhum paciente encontrado com o nome: " + nome);
            }
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
