package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Paciente;

@Repository
public class PacienteRepository {
    
    private JdbcTemplate jdbcTemplate;

    public PacienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserir(Paciente p) {
        String sql = "INSERT INTO paciente (cpf, numPlanoSaude) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                p.getCpf(),
                p.getNumPlanoSaude());
    }

    public List<Paciente> listar() {
        String sql = """
                    SELECT p.*, pa.numPlanoSaude
                    FROM pessoa p
                    JOIN paciente pa ON p.cpf = pa.cpf
                """;

        return jdbcTemplate.query(sql, (r, i) -> {
            Paciente p = new Paciente();

            p.setCpf(r.getString("cpf"));
            p.setNome(r.getString("nome"));
            p.setRua(r.getString("rua"));
            p.setCep(r.getString("cep"));
            p.setBairro(r.getString("bairro"));
            p.setNumero(r.getString("numero"));
            p.setDataNascimento(r.getDate("data_nascimento"));

            p.setNumPlanoSaude(r.getString("numPlanoSaude"));

            p.setTelefones(buscarTelefones(p.getCpf()));

            return p;
        });
    }

    public Paciente buscarPorCpf(String cpf) {
        String sql = """
                    SELECT p.*, pa.numPlanoSaude
                    FROM pessoa p
                    JOIN paciente pa ON p.cpf = pa.cpf
                    WHERE p.cpf = ?
                """;

        List<Paciente> lista = jdbcTemplate.query(sql, (r, i) -> {
            Paciente p = new Paciente();

            p.setCpf(r.getString("cpf"));
            p.setNome(r.getString("nome"));
            p.setRua(r.getString("rua"));
            p.setCep(r.getString("cep"));
            p.setBairro(r.getString("bairro"));
            p.setNumero(r.getString("numero"));
            p.setDataNascimento(r.getDate("data_nascimento"));
            p.setNumPlanoSaude(r.getString("numPlanoSaude"));

            p.setTelefones(buscarTelefones(p.getCpf()));

            return p;
        }, cpf);

        return lista.isEmpty() ? null : lista.get(0);
    }

    public void deletar(String cpf) {
        String sql = "DELETE FROM paciente WHERE cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }

    public void atualizar(String cpf, Paciente p) {
        String sql = "UPDATE paciente SET numPlanoSaude = ? WHERE cpf = ?";

        jdbcTemplate.update(sql,
                p.getNumPlanoSaude(),
                cpf);
    }

    public boolean existe(String cpf) {
        String sql = "SELECT COUNT(*) FROM paciente WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }
    private List<String> buscarTelefones(String cpf) {
        String sql = "SELECT telefone FROM telefone WHERE cpf = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("telefone"),
                cpf);
    }

    public boolean temConsulta(String cpf) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE cpfPaciente = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    public void preencherFormularioSaude(String cpf, String alergias, String doencas, String medicamentos) {
        String sql = "INSERT INTO formulariosaude (cpfPaciente, alergia, medicamento, doencas) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                cpf,
                alergias,
                medicamentos,
                doencas);
    }

}
