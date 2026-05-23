package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.PacienteAlertaSaudeDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.FormularioSaude;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.FormularioSaudeService;

@RestController
@RequestMapping("/formulario-saude")
public class FormularioSaudeController {
    private final FormularioSaudeService formularioSaudeService;

    public FormularioSaudeController(FormularioSaudeService formularioSaudeService) {
        this.formularioSaudeService = formularioSaudeService;
    }

    @GetMapping("/listar")
    public List<FormularioSaude> listar() {
        return formularioSaudeService.listar();
    }

    @GetMapping("/buscar/{cpf}")
    public List<FormularioSaude> buscarPorCpf(@PathVariable String cpf) {
        return formularioSaudeService.buscarPorCpf(cpf);
    }

    @GetMapping("/alerta-saude")
    public ResponseEntity<?> getPacientesComAlertaSaude() {
        List<PacienteAlertaSaudeDTO> resultado = formularioSaudeService.buscarPacientesComAlertaSaude();
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("Nenhum paciente com alerta de saúde encontrado.");
        }
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/paciente/{cpf}")
    public ResponseEntity<String> deletarPorCpf(@PathVariable String cpf) {
        boolean ok = formularioSaudeService.deletarPorCpf(cpf);
        return ok
                ? ResponseEntity.ok("Formulário(s) deletado(s) com sucesso.")
                : ResponseEntity.ok("Nenhum formulário encontrado para este CPF.");
    }

    @DeleteMapping("/{idFormulario}")
    public ResponseEntity<String> deletarPorId(@PathVariable int idFormulario) {
        boolean ok = formularioSaudeService.deletarPorId(idFormulario);
        return ok
                ? ResponseEntity.ok("Formulário deletado com sucesso.")
                : ResponseEntity.ok("Formulário não encontrado.");
    }

}
