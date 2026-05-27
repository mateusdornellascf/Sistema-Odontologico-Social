package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.AlertaAtendimentoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.LogConsultaDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.OperacoesBdRepository;

@Service
public class OperacoesBdService {

    private final OperacoesBdRepository repository;

    public OperacoesBdService(OperacoesBdRepository repository) {
        this.repository = repository;
    }

    public Integer calcularIdade(String cpf) {
        return repository.calcularIdadePessoa(cpf);
    }

    public String classificarRisco(String cpf) {
        return repository.classificarRiscoSaude(cpf);
    }

    public List<AlertaAtendimentoDTO> gerarAlertasDoDia(LocalDate data) {
        return repository.gerarAlertasConsultasDoDia(data);
    }

    public String chamarRemarcacaoViaProc(int idConsulta, LocalDate data, LocalTime hora) {
        return repository.chamarSpRemarcarConsulta(idConsulta, data, hora);
    }

    public List<AlertaAtendimentoDTO> listarAlertas(LocalDate data) {
        return repository.listarAlertas(data);
    }

    public List<LogConsultaDTO> listarLogConsulta(Integer idConsulta) {
        return repository.listarLogConsulta(idConsulta);
    }
}
