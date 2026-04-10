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
@RequestMapping("/consultas/{idConsulta}/procedimentos")
public class ProcedimentoController {

    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }

    @PostMapping("/cirurgico")
    public Map<String, Object> criarCirurgico(
            @PathVariable Long idConsulta,
            @RequestBody CirurgicoDTO dto) {

        Long id = service.criarCirurgico(idConsulta, dto);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento cirúrgico criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @PostMapping("/estetico")
    public Map<String, Object> criarEstetico(
            @PathVariable Long idConsulta,
            @RequestBody EsteticoDTO dto) {

        Long id = service.criarEstetico(idConsulta, dto);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento estético criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @PostMapping("/rotina")
    public Map<String, Object> criarRotina(
            @PathVariable Long idConsulta,
            @RequestBody RotinaDTO dto) {

        Long id = service.criarRotina(idConsulta, dto);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Procedimento de rotina criado com sucesso!");
        resposta.put("idProcedimento", id);

        return resposta;
    }

    @GetMapping
    public List<Procedimento> listar(@PathVariable Long idConsulta) {
        return service.buscarPorConsulta(idConsulta);
    }

    @GetMapping("/{idProcedimento}")
    public Procedimento buscarPorId(
            @PathVariable Long idConsulta,
            @PathVariable Long idProcedimento) {

        return service.buscarPorId(idProcedimento, idConsulta);
    }

    @PutMapping("/{idProcedimento}")
    public String atualizar(
            @PathVariable Long idConsulta,
            @PathVariable Long idProcedimento,
            @RequestBody Procedimento p) {

        p.setIdProcedimento(idProcedimento);
        p.setIdConsulta(idConsulta);

        boolean atualizado = service.atualizar(p);

        return atualizado
                ? "Procedimento atualizado com sucesso!"
                : "Procedimento não encontrado!";
    }

    @DeleteMapping("/{idProcedimento}")
    public String deletar(
            @PathVariable Long idConsulta,
            @PathVariable Long idProcedimento) {
        boolean deletado = service.deletar(idConsulta, idProcedimento);
        return deletado
                ? "Procedimento deletado com sucesso!"
                : "Procedimento não encontrado!";
    }
}