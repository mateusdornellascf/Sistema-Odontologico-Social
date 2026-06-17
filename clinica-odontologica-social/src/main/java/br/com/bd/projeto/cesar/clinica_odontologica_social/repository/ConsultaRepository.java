package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.HistoricoConsultaPacienteDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Consulta;

@Repository
public class ConsultaRepository {
    private final JdbcTemplate jdbcTemplate;

    public ConsultaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void criarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora) {
        if (existeConsultaNoHorario(cpfDentista, data, hora)) {
            throw new RuntimeException("Este horário não está disponível. O dentista já possui uma consulta marcada neste dia e hora.");
        }
        String sql = "INSERT INTO consulta (cpfPaciente, cpfDentista, dataConsulta, horaConsulta) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cpfPaciente, cpfDentista, data, hora);
    }

    public boolean existeConsultaNoHorario(String cpfDentista, LocalDate data, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE cpfDentista = ? AND dataConsulta = ? AND horaConsulta = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                cpfDentista,
                data,
                hora);

        return count != null && count > 0;
    }

    public boolean verificarConsultaExiste(int idConsulta) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE idConsulta = ?";
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

    public String remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        String sql = "CALL sp_remarcar_consulta(?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, String.class, idConsulta, novaData, novaHora);
    }

    public List<Consulta> listar() {
        String sql = "SELECT * FROM consulta";

        return jdbcTemplate.query(sql, (r, i) -> {
            Consulta c = new Consulta();

            c.setIdConsulta(r.getInt("idConsulta"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());

            return c;
        });
    }

    public List<Consulta> listarPorPaciente(String cpfPaciente) {
        String sql = "SELECT * FROM consulta WHERE cpfPaciente = ?";

        return jdbcTemplate.query(sql, (r, i) -> {
            Consulta c = new Consulta();
            c.setIdConsulta(r.getInt("idConsulta"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());
            return c;
        }, cpfPaciente);
    }

    public List<Consulta> listarPorDentista(String cpfDentista) {
        String sql = "SELECT * FROM consulta WHERE cpfDentista = ?";

        return jdbcTemplate.query(sql, (r, i) -> {
            Consulta c = new Consulta();
            c.setIdConsulta(r.getInt("id"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());
            return c;
        }, cpfDentista);
    }

    public Consulta buscarPorId(int idConsulta) {
        String sql = "SELECT * FROM consulta WHERE idConsulta = ?";

        return jdbcTemplate.queryForObject(sql, (r, i) -> {
            Consulta c = new Consulta();
            c.setIdConsulta(r.getInt("idConsulta"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());
            return c;
        }, idConsulta);
    }

    public void deletarConsulta(int idConsulta) {
        String sql = "DELETE FROM consulta WHERE idConsulta = ?";
        jdbcTemplate.update(sql, idConsulta);
    }

    public boolean existeProcedimentos(int idConsulta) {
        String sql = "SELECT COUNT(*) FROM procedimento WHERE idConsulta = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idConsulta);
        return count != null && count > 0;
    }

    public List<Consulta> listarConsultasDentista(String cpfDentista) {
        String sql = "SELECT * FROM consulta WHERE cpfDentista = ?";

        return jdbcTemplate.query(sql, (r, i) -> {
            Consulta c = new Consulta();
            c.setIdConsulta(r.getInt("idConsulta"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());
            return c;
        }, cpfDentista);
    }

    public List<Consulta> listarConsultasPaciente(String cpfPaciente) {
        String sql = "SELECT * FROM consulta WHERE cpfPaciente = ?";

        return jdbcTemplate.query(sql, (r, i) -> {
            Consulta c = new Consulta();
            c.setIdConsulta(r.getInt("idConsulta"));
            c.setIdPaciente(r.getString("cpfPaciente"));
            c.setIdDentista(r.getString("cpfDentista"));
            c.setData(r.getDate("dataConsulta").toLocalDate());
            c.setHora(r.getTime("horaConsulta").toLocalTime());
            return c;
        }, cpfPaciente);
    }

    public boolean existeConsulta(Long idConsulta) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE idConsulta = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idConsulta);
        return count != null && count > 0;
    }

    public List<HistoricoConsultaPacienteDTO> buscarHistoricoPorPaciente(String cpfPaciente) {
        String sql = """
                SELECT
                    c.idConsulta,
                    c.cpfPaciente,
                    p_pac.nome AS nomePaciente,
                    c.cpfDentista,
                    p_den.nome AS nomeDentista,
                    c.dataConsulta,
                    c.horaConsulta
                FROM consulta c
                JOIN pessoa p_pac
                    ON c.cpfPaciente = p_pac.cpf
                JOIN pessoa p_den
                    ON c.cpfDentista = p_den.cpf
                WHERE c.cpfPaciente = ?
                ORDER BY c.dataConsulta DESC, c.horaConsulta DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            HistoricoConsultaPacienteDTO dto = new HistoricoConsultaPacienteDTO();
            dto.setIdConsulta(rs.getInt("idConsulta"));
            dto.setCpfPaciente(rs.getString("cpfPaciente"));
            dto.setNomePaciente(rs.getString("nomePaciente"));
            dto.setCpfDentista(rs.getString("cpfDentista"));
            dto.setNomeDentista(rs.getString("nomeDentista"));
            dto.setDataConsulta(rs.getDate("dataConsulta").toLocalDate());
            dto.setHoraConsulta(rs.getTime("horaConsulta").toLocalTime());
            return dto;
        }, cpfPaciente);
    }

    private Consulta mapConsulta(java.sql.ResultSet r) throws java.sql.SQLException {
        Consulta c = new Consulta();
        c.setIdConsulta(r.getInt("idConsulta"));
        c.setIdPaciente(r.getString("cpfPaciente"));
        c.setIdDentista(r.getString("cpfDentista"));
        c.setData(r.getDate("dataConsulta").toLocalDate());
        c.setHora(r.getTime("horaConsulta").toLocalTime());
        return c;
    }

    public List<HistoricoConsultaPacienteDTO> buscarConsultasPorNomePaciente(String nomePaciente) {
        String sql = """
                SELECT
                    c.idConsulta,
                    c.cpfPaciente,
                    p_pac.nome AS nomePaciente,
                    c.cpfDentista,
                    p_den.nome AS nomeDentista,
                    c.dataConsulta,
                    c.horaConsulta
                FROM consulta c
                JOIN pessoa p_pac
                    ON c.cpfPaciente = p_pac.cpf
                JOIN pessoa p_den
                    ON c.cpfDentista = p_den.cpf
                WHERE p_pac.nome LIKE ?
                ORDER BY c.dataConsulta DESC, c.horaConsulta DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            HistoricoConsultaPacienteDTO dto = new HistoricoConsultaPacienteDTO();
            dto.setIdConsulta(rs.getInt("idConsulta"));
            dto.setCpfPaciente(rs.getString("cpfPaciente"));
            dto.setNomePaciente(rs.getString("nomePaciente"));
            dto.setCpfDentista(rs.getString("cpfDentista"));
            dto.setNomeDentista(rs.getString("nomeDentista"));
            dto.setDataConsulta(rs.getDate("dataConsulta").toLocalDate());
            dto.setHoraConsulta(rs.getTime("horaConsulta").toLocalTime());
            return dto;
        }, "%" + nomePaciente + "%");
    }
}
