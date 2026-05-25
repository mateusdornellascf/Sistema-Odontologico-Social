package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.ChatRequestDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.ChatResponseDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.SuggestionsResponseDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.services.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponseDTO perguntar(@RequestBody ChatRequestDTO request) {
        String resposta = chatService.perguntar(request.getPergunta());
        return new ChatResponseDTO(resposta);
    }

    @GetMapping("/suggestions")
    public SuggestionsResponseDTO suggestions() {
        return new SuggestionsResponseDTO(List.of(
                "Quantos pacientes existem?",
                "Quais pacientes têm alerta de saúde?",
                "Quais dentistas estão sem consulta futura?",
                "Quais consultas estão cadastradas?",
                "Existe algum paciente chamado João?"
        ));
    }
}