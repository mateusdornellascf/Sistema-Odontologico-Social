package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;

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
    public void inserirDentista(Dentista d) {

    if (dentistaRepository.existe(d.getCpf())) {
        throw new RuntimeException("Dentista já existe!");
    }
    if (!pessoaRepository.existe(d.getCpf())) {
        pessoaRepository.inserir(d);
    }
    dentistaRepository.inserir(d);
}

    
    public List<Dentista> listar() {
        return dentistaRepository.listar();
    }

    
    public Dentista buscarPorCpf(String cpf) {
        return dentistaRepository.buscarPorCpf(cpf);
    }

    
    public void atualizar(String cpf, Dentista d) {
        pessoaRepository.atualizar(cpf, d);
        dentistaRepository.atualizar(cpf, d);
    }

    
    public void deletar(String cpf) {

    if (dentistaRepository.temConsulta(cpf)) {
        throw new RuntimeException("Dentista possui consultas vinculadas. Remova as consultas antes.");
    }
    dentistaRepository.deletar(cpf);
}
}