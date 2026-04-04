package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Dentista;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.DentistaRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.PessoaRepository;

@Service
public class DentistaService {

    private final DentistaRepository dentistaRepository;
    private final PessoaRepository pessoaRepository;

    public DentistaService(DentistaRepository dentistaRepository, PessoaRepository pessoaRepository) {
        this.dentistaRepository = dentistaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public void inserirDentista(Dentista d) {

        if (dentistaRepository.existe(d.getCpf())) {
            throw new RuntimeException("Dentista já existe!");
        }

        if (!pessoaRepository.existe(d.getCpf())) {
            pessoaRepository.inserir(d);
        } else {
            pessoaRepository.atualizar(d.getCpf(), d);
        }

        dentistaRepository.inserir(d);
    }

    public List<Dentista> listar() {
        return dentistaRepository.listar();
    }

    public Dentista buscarPorCpf(String cpf) {
        return dentistaRepository.buscarPorCpf(cpf);
    }

    @Transactional
    public void atualizar(String cpf, Dentista d) {
        pessoaRepository.atualizar(cpf, d);
        dentistaRepository.atualizar(cpf, d);
    }

    @Transactional
    public void deletar(String cpf) {

        if (dentistaRepository.temConsulta(cpf)) {
            throw new RuntimeException("Dentista possui consultas vinculadas.");
        }

        dentistaRepository.deletar(cpf);
        pessoaRepository.deletar(cpf);
    }
}