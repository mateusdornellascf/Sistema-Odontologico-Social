package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.CirurgicoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.EsteticoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.RotinaDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Procedimento;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.ProcedimentoService;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {

    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }

    @PostMapping("/cirurgico")
    public Map<String, Object> criarCirurgico(@RequestBody CirurgicoDTO request) {

        Long id = service.criarCirurgico(request.getIdConsulta(), request);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento cirúrgico criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @PostMapping("/estetico")
    public Map<String, Object> criarEstetico(@RequestBody EsteticoDTO request) {

        Long id = service.criarEstetico(request.getIdConsulta(), request);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento estético criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @PostMapping("/rotina")
    public Map<String, Object> criarRotina(@RequestBody RotinaDTO request) {

        Long id = service.criarRotina(request.getIdConsulta(), request);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento de rotina criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @GetMapping()
    public List<Procedimento> listar() {
        return service.listar();
    }

    @GetMapping("/{idProcedimento}")
    public Procedimento buscarPorIdProcedimento(@PathVariable Long idProcedimento) {
        return service.buscarPorIdProcedimento(idProcedimento);
    }

    @GetMapping("/consulta/{idConsulta}")
    public List<Procedimento> buscarPorConsulta(@PathVariable Long idConsulta) {
        return service.buscarPorIdConsulta(idConsulta);
    }

    @PutMapping("/{idProcedimento}")
    public String atualizar(
            @PathVariable Long idProcedimento,
            @RequestBody Procedimento p) {

        p.setIdProcedimento(idProcedimento);

        boolean atualizado = service.atualizar(p);

        return atualizado ? "OK" : "ERRO";
    }

    @DeleteMapping("/{idProcedimento}")
    public String deletar(@PathVariable Long idProcedimento) {
        boolean deletado = service.deletar(idProcedimento);
        return deletado ? "OK" : "ERRO";
    }
}