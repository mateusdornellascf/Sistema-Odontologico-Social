package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.FormularioSaude;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.FormularioSaudeRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PacienteRepository;

@Service
public class FormularioSaudeService {
    private final PacienteRepository pacienteRepository;

    private final FormularioSaudeRepository formularioSaudeRepository;

    public FormularioSaudeService(FormularioSaudeRepository formularioSaudeRepository, PacienteRepository pacienteRepository) {
        this.formularioSaudeRepository = formularioSaudeRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void salvarOuAtualizar(String cpf, String alergia, String doencas, String medicamento) {

        if (!pacienteRepository.existe(cpf)) {
            throw new RuntimeException("Paciente não encontrado!");
        }
        
        if (formularioSaudeRepository.existe(cpf)) {
            formularioSaudeRepository.atualizar(cpf, alergia, doencas, medicamento);
        } else {
            formularioSaudeRepository.salvar(cpf, alergia, doencas, medicamento);
        }
    }

    public List<FormularioSaude> listar() {
        return formularioSaudeRepository.listar();
    }

    public List<FormularioSaude> buscarPorCpf(String cpf) {
        return formularioSaudeRepository.buscarPorCpf(cpf);
    }

}
