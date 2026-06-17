package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.HistoricoConsultaPacienteDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Consulta;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public String criarConsulta(@RequestBody Map<String, String> body) {
        service.criarConsulta(
                body.get("idPaciente"),
                body.get("idDentista"),
                LocalDate.parse(body.get("dataConsulta")),
                LocalTime.parse(body.get("horaConsulta")));
        return "Consulta criada com sucesso!";
    }

    @GetMapping
    public List<Consulta> listarConsultas() {
        return service.listarConsultas();
    }

    @PutMapping("/remarcar/{idConsulta}")
    public String remarcarConsulta(
            @PathVariable int idConsulta,
            @RequestBody Map<String, String> body) {

        LocalDate novaData = LocalDate.parse(body.get("data"));
        LocalTime novaHora = LocalTime.parse(body.get("hora"));

        return service.remarcarConsulta(idConsulta, novaData, novaHora);
    }

    @DeleteMapping("/{idConsulta}")
    public String deletarConsulta(@PathVariable int idConsulta) {
        service.deletarConsulta(idConsulta);
        return "Consulta deletada com sucesso!";
    }

    @GetMapping("/dentista/{cpfDentista}")
    public List<Consulta> listarConsultasDentista(@PathVariable String cpfDentista) {
        return service.listarConsultasDentista(cpfDentista);
    }

    @GetMapping("/paciente/{cpfPaciente}")
    public List<Consulta> listarConsultasPaciente(@PathVariable String cpfPaciente) {
        return service.listarConsultasPaciente(cpfPaciente);
    }

    @GetMapping("/historico/{cpfPaciente}")
    public ResponseEntity<?> getHistoricoPaciente(@PathVariable String cpfPaciente) {
        List<HistoricoConsultaPacienteDTO> resultado = service.buscarHistoricoPorPaciente(cpfPaciente);
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("Nenhuma consulta encontrada para o CPF: " + cpfPaciente);
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscar-por-nome-paciente")
    public ResponseEntity<?> buscarConsultasPorNomePaciente(@RequestParam String nome) {
        try {
            List<HistoricoConsultaPacienteDTO> resultado = service.buscarConsultasPorNomePaciente(nome);
            if (resultado.isEmpty()) {
                return ResponseEntity.ok("Nenhuma consulta encontrada para pacientes com nome: " + nome);
            }
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
