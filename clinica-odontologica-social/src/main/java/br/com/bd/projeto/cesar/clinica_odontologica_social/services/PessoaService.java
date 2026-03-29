package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Pessoa;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public void inserirPessoa(Pessoa pessoa) {
        pessoaRepository.inserir(pessoa);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.listar();
    }

    public Pessoa buscarPorCpf(String cpf) {
        return pessoaRepository.buscarPorCpf(cpf);
    }

    public void deletar(String cpf) {
        pessoaRepository.deletar(cpf);
    }

    public void atualizar(String cpf, Pessoa pessoa) {
        pessoaRepository.atualizar(cpf, pessoa);
    }

}
