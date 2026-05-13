package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.DentistaAtivoPorConsultasDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Dentista;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.DentistaService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/dentista")
public class DentistaController {

    private final DentistaService service;

    public DentistaController(DentistaService service) {
        this.service = service;
    }

    @PostMapping
    public String inserirDentista(@RequestBody Dentista dentista) {
        service.inserirDentista(dentista);
        return "Dentista inserido!";
    }

    @GetMapping("/listar")
    public List<Dentista> listarDentistas() {
        return service.listar();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Dentista> buscarPorCpf(@PathVariable String cpf) {
        Dentista p = service.buscarPorCpf(cpf);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{cpf}")
    public String atualizar(@PathVariable String cpf, @RequestBody Dentista dentista) {
        service.atualizar(cpf, dentista);
        return "Dentista atualizado";
    }

    @DeleteMapping("/{cpf}")
    public String deletarDentista(@PathVariable String cpf) {
        service.deletar(cpf);
        return "Dentista deletado";
    }

    @GetMapping("/mais-ativos")
    public ResponseEntity<?> getDentistasMaisAtivos(
            @RequestParam(defaultValue = "1") int minConsultas) {
        List<DentistaAtivoPorConsultasDTO> resultado = service.buscarDentistasMaisAtivos(minConsultas);
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("Nenhum dentista com " + minConsultas + " ou mais consultas.");
        }
        return ResponseEntity.ok(resultado);
    }

    
    @GetMapping("/sem-consulta-futura")
    public ResponseEntity<?> getDentistasSemConsultaFutura() {
        List<Dentista> resultado = service.buscarDentistasSemConsultaFutura();
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("Todos os dentistas possuem consultas futuras agendadas.");
        }
        return ResponseEntity.ok(resultado);
    }
}
