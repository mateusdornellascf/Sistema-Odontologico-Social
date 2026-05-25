package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.services.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/resumo")
    public Map<String, Object> resumo() {
        return service.resumo();
    }

    @GetMapping("/consultas-por-mes")
    public List<Map<String, Object>> consultasPorMes(
            @RequestParam(required = false) Integer ano) {
        return service.consultasPorMes(ano);
    }

    @GetMapping("/consultas-por-dentista")
    public List<Map<String, Object>> consultasPorDentista(
            @RequestParam(defaultValue = "10") int limit) {
        return service.consultasPorDentista(limit);
    }

    @GetMapping("/risco-distribuicao")
    public List<Map<String, Object>> distribuicaoRisco() {
        return service.distribuicaoRisco();
    }

    @GetMapping("/procedimentos-por-tipo")
    public List<Map<String, Object>> procedimentosPorTipo() {
        return service.procedimentosPorTipo();
    }

    @GetMapping("/consultas-por-especialidade")
    public List<Map<String, Object>> consultasPorEspecialidade() {
        return service.consultasPorEspecialidade();
    }

    @GetMapping("/idade-stats")
    public Map<String, Object> estatisticasIdade() {
        return service.estatisticasIdade();
    }

    @GetMapping("/pacientes-por-bairro")
    public List<Map<String, Object>> pacientesPorBairro() {
        return service.pacientesPorBairro();
    }

    @GetMapping("/correlacao-consultas-procedimentos")
    public List<Map<String, Object>> correlacaoCxP() {
        return service.consultasVsProcedimentosPorDentista();
    }
}
