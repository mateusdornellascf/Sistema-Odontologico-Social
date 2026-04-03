package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.jdbc.core.JdbcTemplate;

public class ConsultaRepository {
    private final JdbcTemplate jdbcTemplate;

    public ConsultaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void criarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora) {
        if (!existePaciente(cpfPaciente)) {
            throw new RuntimeException("Paciente não encontrado");
        }
        if (!existeDentista(cpfDentista)) {
            throw new RuntimeException("Dentista não encontrado");
        }
        // if (existeConsultaNoHorario(cpfDentista, data, hora)) {
        // throw new RuntimeException("Horário indisponível");
        // }
        String sql = "INSERT INTO consulta (cpf_paciente, cpf_dentista, data, hora) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, cpfPaciente, cpfDentista, data, hora);
    }

    public boolean verificarConsultaExiste(int idConsulta) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idConsulta) > 0;
    }

    public boolean existePaciente(String cpfPaciente) {
        String sql = "SELECT COUNT(*) FROM paciente WHERE cpf = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cpfPaciente) > 0;
    }

    public boolean existeDentista(String cpfDentista) {
        String sql = "SELECT COUNT(*) FROM dentista WHERE cpf = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cpfDentista) > 0;
    }

    public void remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        String sql = "UPDATE consulta SET data = ?, hora = ? WHERE id = ?";
        jdbcTemplate.update(sql, novaData, novaHora, idConsulta);
        
    }

}
