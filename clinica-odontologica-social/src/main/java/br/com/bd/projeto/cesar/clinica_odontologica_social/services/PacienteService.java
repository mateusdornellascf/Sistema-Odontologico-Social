package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Paciente;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PacienteRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PessoaRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PessoaRepository pessoaRepository;

    public PacienteService(PacienteRepository pacienteRepository, PessoaRepository pessoaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public void inserirPaciente(Paciente p) {

    if (!pessoaRepository.existe(p.getCpf())) {
        pessoaRepository.inserir(p);
    }
   
    if (pacienteRepository.existe(p.getCpf())) {
        throw new RuntimeException("Paciente já existe!");
    }
    pacienteRepository.inserir(p);
}
    
    public List<Paciente> listar() {
        return pacienteRepository.listar();
    }

    
    public Paciente buscarPorCpf(String cpf) {
        return pacienteRepository.buscarPorCpf(cpf);
    }

    
    @Transactional
    public void atualizar(String cpf, Paciente p) {
        pessoaRepository.atualizar(cpf, p);
        pacienteRepository.atualizar(cpf, p);
    }

    @Transactional
    public void deletar(String cpf) {
        if (pacienteRepository.temConsulta(cpf)) {
            throw new RuntimeException("Dentista possui consultas vinculadas.");
        }

        pacienteRepository.deletar(cpf);
        pessoaRepository.deletar(cpf);

    }
}