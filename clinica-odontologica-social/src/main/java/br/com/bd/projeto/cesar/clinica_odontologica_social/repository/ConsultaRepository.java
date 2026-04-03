package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Consulta;

@Repository
public class ConsultaRepository {
    private final JdbcTemplate jdbcTemplate;

    public ConsultaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void criarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora) {
        if (existeConsultaNoHorario(cpfDentista, data, hora)) {
            throw new RuntimeException("Horário indisponível");
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

    public void remarcarConsulta(int idConsulta, LocalDate novaData, LocalTime novaHora) {
        if (!verificarConsultaExiste(idConsulta)) {
            throw new RuntimeException("Consulta não encontrada");
        }

        String sql = "UPDATE consulta SET dataConsulta = ?, horaConsulta = ? WHERE idConsulta = ?";
        jdbcTemplate.update(sql, novaData, novaHora, idConsulta);
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
}
