package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.ConsultaRepository;

public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }
    public void criarConsulta(String cpfPaciente, String cpfDentista,LocalDate data, LocalTime hora){
        repository.criarConsulta(cpfPaciente, cpfDentista, data, hora);
    }

    public String verificarConsultaExiste(int idConsulta) {
        if(!repository.verificarConsultaExiste(idConsulta)){
            return "Consulta não encontrada!";
        }
        return "Consulta encontrada";
    }

    public void remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        repository.remarcarConsulta(idConsulta, novaData, novaHora);
    }

    public void existePaciente(String cpfPaciente) {
        repository.existePaciente(cpfPaciente);
    }

    public void existeDentista(String cpfDentista) {
        repository.existeDentista(cpfDentista);
    }
}
