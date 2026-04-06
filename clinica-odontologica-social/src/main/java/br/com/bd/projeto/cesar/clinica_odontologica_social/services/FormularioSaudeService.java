package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.FormularioSaudeRepository;

@Service
public class FormularioSaudeService {
    private final FormularioSaudeRepository formularioSaudeRepository;

    public FormularioSaudeService(FormularioSaudeRepository formularioSaudeRepository) {
        this.formularioSaudeRepository = formularioSaudeRepository;
    }

    public void listar(){
        formularioSaudeRepository.listar();
    }
    
}
