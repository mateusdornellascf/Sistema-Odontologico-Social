package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Paciente;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.FormularioSaudeRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PacienteRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PessoaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PessoaRepository pessoaRepository;
    private final FormularioSaudeRepository formularioRepository;

    public PacienteService(PacienteRepository pacienteRepository, PessoaRepository pessoaRepository,
            FormularioSaudeRepository formularioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pessoaRepository = pessoaRepository;
        this.formularioRepository = formularioRepository;
    }

    @Transactional
    public void inserirPaciente(Paciente p) {

        if (!pessoaRepository.existe(p.getCpf())) {
            pessoaRepository.inserir(p);
        }

        if (pacienteRepository.existe(p.getCpf())) {
            throw new RuntimeException("Este paciente já está cadastrado no sistema. Atualize seus dados se necessário.");
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
            throw new RuntimeException("Não é possível deletar este paciente, pois possui consultas marcadas. Remova as consultas antes de prosseguir.");
        }

        pacienteRepository.deletar(cpf);
        pessoaRepository.deletar(cpf);

    }

    public void preencherFormularioSaude(String cpf, String alergias, String doencas, String medicamentos) {
        if (!pacienteRepository.existe(cpf)) {
            throw new RuntimeException("Paciente não encontrado. Verifique o CPF digitado.");
        }

        if (formularioRepository.existe(cpf)) {
            throw new RuntimeException("Este paciente já possui um formulário preenchido. Atualize o formulário existente.");
        }
        formularioRepository.salvar(cpf, alergias, doencas, medicamentos);
    }

    public List<Paciente> buscarPacientesSemConsulta() {
        return pacienteRepository.buscarPacientesSemConsulta();
    }

    public List<Paciente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Por favor, digite um nome para buscar.");
        }
        return pacienteRepository.buscarPorNome(nome);
    }

}