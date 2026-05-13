package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.PacienteAlertaSaudeDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.FormularioSaude;

@Repository
public class FormularioSaudeRepository {

    private final JdbcTemplate jdbcTemplate;

    public FormularioSaudeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(String cpf, String alergias, String doencas, String medicamentos) {
        String sql = "INSERT INTO formulariosaude (cpfPaciente, alergia, doencas, medicamento) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cpf, alergias, doencas, medicamentos);
    }

    public List<FormularioSaude> listar() {
        String sql = "SELECT * FROM formulariosaude";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            FormularioSaude f = new FormularioSaude();

            f.setIdFormulario(rs.getInt("idFormulario"));
            f.setCpfPaciente(rs.getString("cpfPaciente"));
            f.setAlergias(rs.getString("alergia"));
            f.setDoencas(rs.getString("doencas"));
            f.setMedicamentos(rs.getString("medicamento"));

            return f;
        });
    }

    public List<FormularioSaude> buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM formulariosaude WHERE cpfPaciente = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            FormularioSaude f = new FormularioSaude();
            f.setIdFormulario(rs.getInt("idFormulario"));
            f.setCpfPaciente(rs.getString("cpfPaciente"));
            f.setAlergias(rs.getString("alergia"));
            f.setDoencas(rs.getString("doencas"));
            f.setMedicamentos(rs.getString("medicamento"));
            return f;
        }, cpf);
    }

    public void atualizar(String cpf, String alergia, String doencas, String medicamento) {
    String sql = "UPDATE formulariosaude SET alergia = ?, doencas = ?, medicamento = ? WHERE cpfPaciente = ?";

    jdbcTemplate.update(sql, alergia, doencas, medicamento, cpf);
}

    public boolean existe(String cpf) {
        String sql = "SELECT COUNT(*) FROM formulariosaude WHERE cpfPaciente = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    public List<PacienteAlertaSaudeDTO> buscarPacientesComAlertaSaude() {
        String sql = "SELECT * FROM vw_pacientes_com_alerta_saude ORDER BY nome ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PacienteAlertaSaudeDTO dto = new PacienteAlertaSaudeDTO();
            dto.setCpf(rs.getString("cpf"));
            dto.setNome(rs.getString("nome"));
            dto.setAlergia(rs.getString("alergia"));
            dto.setDoencas(rs.getString("doencas"));
            dto.setMedicamento(rs.getString("medicamento"));
            return dto;
        });
    }

    private FormularioSaude mapFormulario(java.sql.ResultSet rs) throws java.sql.SQLException {
        FormularioSaude f = new FormularioSaude();
        f.setIdFormulario(rs.getInt("idFormulario"));
        f.setCpfPaciente(rs.getString("cpfPaciente"));
        f.setAlergias(rs.getString("alergia"));
        f.setDoencas(rs.getString("doencas"));
        f.setMedicamentos(rs.getString("medicamento"));
        return f;
    }

}
