package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Consulta;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void criarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora) {
        validarPaciente(cpfPaciente);
        validarDentista(cpfDentista);
        validarHorario(cpfDentista, data, hora);

        repository.criarConsulta(cpfPaciente, cpfDentista, data, hora);
    }

    public String verificarConsultaExiste(int idConsulta) {
        if (!repository.verificarConsultaExiste(idConsulta)) {
            return "Consulta não encontrada!";
        }
        return "Consulta encontrada";
    }

    public List<Consulta> listarConsultas() {
        return repository.listar();
    }

    public void remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        repository.remarcarConsulta(idConsulta, novaData, novaHora);
    }

    public void validarPaciente(String cpfPaciente) {
        if (!repository.existePaciente(cpfPaciente)) {
            throw new RuntimeException("Paciente não encontrado");
        }
    }

    public void validarDentista(String cpfDentista) {
        if (!repository.existeDentista(cpfDentista)) {
            throw new RuntimeException("Dentista não encontrado");
        }
    }

    public void validarHorario(String cpfDentista, LocalDate data, LocalTime hora) {
        if (repository.existeConsultaNoHorario(cpfDentista, data, hora)) {
            throw new RuntimeException("Horário indisponível");
        }
    }

    public void deletarConsulta(int idConsulta) {
        if (repository.existeProcedimentos(idConsulta)) {
            throw new RuntimeException("Não é possível deletar: existem procedimentos associados.");
        }
        repository.deletarConsulta(idConsulta);
    }

    public List<Consulta> listarConsultasDentista(String cpfDentista) {
        validarDentista(cpfDentista); // garante que o dentista existe
        return repository.listarConsultasDentista(cpfDentista);
    }

    public List<Consulta> listarConsultasPaciente(String cpfPaciente) {
        validarPaciente(cpfPaciente); // garante que o paciente existe
        return repository.listarConsultasPaciente(cpfPaciente);
    }
}
