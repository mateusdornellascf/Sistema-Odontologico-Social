package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.DentistaAtivoPorConsultasDTO;
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
            throw new RuntimeException("Este dentista já está cadastrado no sistema. Atualize seus dados se necessário.");
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
            throw new RuntimeException("Não é possível deletar este dentista, pois possui consultas marcadas. Remova as consultas antes de prosseguir.");
        }

        dentistaRepository.deletar(cpf);
        pessoaRepository.deletar(cpf);
    }

    public List<DentistaAtivoPorConsultasDTO> buscarDentistasMaisAtivos(int minConsultas) {
        return dentistaRepository.buscarDentistasMaisAtivos(minConsultas);
    }

    public List<Dentista> buscarDentistasSemConsultaFutura() {
        return dentistaRepository.buscarDentistasSemConsultaFutura();
    }
}