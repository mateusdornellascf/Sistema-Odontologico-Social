package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.services.FormularioSaudeService;

@RestController
@RequestMapping("/formularios")
public class FormularioSaudeController {
    private final FormularioSaudeService formularioSaudeService;
    
    public FormularioSaudeController(FormularioSaudeService formularioSaudeService) {
        this.formularioSaudeService = formularioSaudeService;
    }
    
    @GetMapping("/listar")
    public void listar(){
        formularioSaudeService.listar();
    }
}
