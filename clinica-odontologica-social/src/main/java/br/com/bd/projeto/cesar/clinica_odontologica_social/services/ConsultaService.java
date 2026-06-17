package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.HistoricoConsultaPacienteDTO;
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

    public String remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        return repository.remarcarConsulta(idConsulta, novaData, novaHora);
    }

    public void validarPaciente(String cpfPaciente) {
        if (!repository.existePaciente(cpfPaciente)) {
            throw new RuntimeException("Paciente não encontrado. Verifique o CPF digitado.");
        }
    }

    public void validarDentista(String cpfDentista) {
        if (!repository.existeDentista(cpfDentista)) {
            throw new RuntimeException("Dentista não encontrado. Verifique o CPF digitado.");
        }
    }

    public void validarHorario(String cpfDentista, LocalDate data, LocalTime hora) {
        if (repository.existeConsultaNoHorario(cpfDentista, data, hora)) {
            throw new RuntimeException("Horário indisponível. O dentista já possui uma consulta marcada neste horário.");
        }
    }

    public void deletarConsulta(int idConsulta) {
        if (repository.existeProcedimentos(idConsulta)) {
            throw new RuntimeException("Não é possível deletar esta consulta, pois possui procedimentos associados. Remova os procedimentos antes de prosseguir.");
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

    public List<HistoricoConsultaPacienteDTO> buscarHistoricoPorPaciente(String cpfPaciente) {
        return repository.buscarHistoricoPorPaciente(cpfPaciente);
    }

    public List<HistoricoConsultaPacienteDTO> buscarConsultasPorNomePaciente(String nomePaciente) {
        if (nomePaciente == null || nomePaciente.trim().isEmpty()) {
            throw new RuntimeException("Por favor, digite o nome do paciente para buscar.");
        }
        return repository.buscarConsultasPorNomePaciente(nomePaciente);
    }
}
