package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Pessoa;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public void inserirPessoa(Pessoa pessoa) {
        repository.inserir(pessoa);
    }

    public List<Pessoa> listar() {
        return repository.listar();
    }

    public Pessoa buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }

    public void deletar(String cpf) {
        repository.deletar(cpf);
    }

    public void atualizar(String cpf, Pessoa pessoa) {
        repository.atualizar(cpf, pessoa);
    }

}
