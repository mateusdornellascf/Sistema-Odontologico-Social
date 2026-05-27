package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.AlertaAtendimentoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.LogConsultaDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.OperacoesBdService;

@RestController
@RequestMapping("/bd")
public class OperacoesBdController {

    private final OperacoesBdService service;

    public OperacoesBdController(OperacoesBdService service) {
        this.service = service;
    }

    @GetMapping("/funcoes/idade/{cpf}")
    public ResponseEntity<Map<String, Object>> calcularIdade(@PathVariable String cpf) {
        Integer idade = service.calcularIdade(cpf);
        Map<String, Object> resp = new HashMap<>();
        resp.put("cpf", cpf);
        resp.put("idade", idade);
        resp.put("funcao", "func_calcular_idade_pessoa");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/funcoes/risco/{cpf}")
    public ResponseEntity<Map<String, Object>> classificarRisco(@PathVariable String cpf) {
        String risco = service.classificarRisco(cpf);
        Map<String, Object> resp = new HashMap<>();
        resp.put("cpf", cpf);
        resp.put("classificacao", risco);
        resp.put("funcao", "func_classificar_risco_saude");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/procedures/gerar-alertas")
    public ResponseEntity<Map<String, Object>> gerarAlertas(@RequestBody Map<String, String> body) {
        LocalDate data = LocalDate.parse(body.get("data"));
        List<AlertaAtendimentoDTO> alertas = service.gerarAlertasDoDia(data);
        Map<String, Object> resp = new HashMap<>();
        resp.put("procedure", "sp_gerar_alertas_consultas_do_dia");
        resp.put("data", data.toString());
        resp.put("totalAlertasNaData", alertas.size());
        resp.put("alertas", alertas);
        resp.put("mensagem",
                "Procedure executada. Os alertas foram calculados sem criar tabela permanente.");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/procedures/remarcar")
    public ResponseEntity<Map<String, Object>> remarcarViaProc(@RequestBody Map<String, String> body) {
        int id = Integer.parseInt(body.get("idConsulta"));
        LocalDate data = LocalDate.parse(body.get("data"));
        LocalTime hora = LocalTime.parse(body.get("hora"));
        String msg = service.chamarRemarcacaoViaProc(id, data, hora);
        Map<String, Object> resp = new HashMap<>();
        resp.put("procedure", "sp_remarcar_consulta");
        resp.put("mensagem", msg);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/alertas")
    public List<AlertaAtendimentoDTO> listarAlertas(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return service.listarAlertas(data);
    }

    @GetMapping("/log-consulta")
    public List<LogConsultaDTO> listarLogConsulta(
            @RequestParam(required = false) Integer idConsulta) {
        return service.listarLogConsulta(idConsulta);
    }
}
