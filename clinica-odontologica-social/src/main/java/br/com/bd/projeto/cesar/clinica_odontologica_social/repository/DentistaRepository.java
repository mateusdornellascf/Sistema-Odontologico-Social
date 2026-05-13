package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.DentistaAtivoPorConsultasDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Dentista;

@Repository
public class DentistaRepository {

    private final JdbcTemplate jdbcTemplate;

    public DentistaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserir(Dentista d) {
        String sql = "INSERT INTO dentista (cpf, cro, especialidade, email, coordenador) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                d.getCpf(),
                d.getCro(),
                d.getEspecialidade(),
                d.getEmail(),
                d.getCoordenador());
    }

    public List<Dentista> listar() {
        String sql = """
                    SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
                    FROM pessoa p
                    JOIN dentista d ON p.cpf = d.cpf
                """;

        return jdbcTemplate.query(sql, (r, i) -> {
            Dentista d = new Dentista();

            d.setCpf(r.getString("cpf"));
            d.setNome(r.getString("nome"));
            d.setRua(r.getString("rua"));
            d.setCep(r.getString("cep"));
            d.setBairro(r.getString("bairro"));
            d.setNumero(r.getString("numero"));

            Date dataSql = r.getDate("data_nascimento");
            if (dataSql != null) {
                d.setDataNascimento(new Date(dataSql.getTime()));
            }

            d.setCro(r.getString("cro"));
            d.setEspecialidade(r.getString("especialidade"));
            d.setEmail(r.getString("email"));
            d.setCoordenador(r.getString("coordenador"));

            d.setTelefones(buscarTelefones(d.getCpf()));

            return d;
        });
    }

    public Dentista buscarPorCpf(String cpf) {
        String sql = """
                    SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
                    FROM pessoa p
                    JOIN dentista d ON p.cpf = d.cpf
                    WHERE p.cpf = ?
                """;

        List<Dentista> lista = jdbcTemplate.query(sql, (r, i) -> {
            Dentista d = new Dentista();

            d.setCpf(r.getString("cpf"));
            d.setNome(r.getString("nome"));
            d.setRua(r.getString("rua"));
            d.setCep(r.getString("cep"));
            d.setBairro(r.getString("bairro"));
            d.setNumero(r.getString("numero"));

            Date dataSql = r.getDate("data_nascimento");
            if (dataSql != null) {
                d.setDataNascimento(new Date(dataSql.getTime()));
            }

            d.setCro(r.getString("cro"));
            d.setEspecialidade(r.getString("especialidade"));
            d.setEmail(r.getString("email"));
            d.setCoordenador(r.getString("coordenador"));

            d.setTelefones(buscarTelefones(d.getCpf()));

            return d;
        }, cpf);

        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean temConsulta(String cpf) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE cpfDentista = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    public void deletar(String cpf) {
        String sql = "DELETE FROM dentista WHERE cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }

    public void atualizar(String cpf, Dentista d) {
        String sql = """
                    UPDATE dentista
                    SET cro = ?, especialidade = ?, email = ?, coordenador = ?
                    WHERE cpf = ?
                """;

        jdbcTemplate.update(sql,
                d.getCro(),
                d.getEspecialidade(),
                d.getEmail(),
                d.getCoordenador(),
                cpf);
    }

    public boolean existe(String cpf) {
        String sql = "SELECT COUNT(*) FROM dentista WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    private List<String> buscarTelefones(String cpf) {
    String sql = "SELECT telefone FROM telefone WHERE cpf = ?";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> rs.getString("telefone"),
        cpf
    );
    }

    public List<DentistaAtivoPorConsultasDTO> buscarDentistasMaisAtivos(int minConsultas) {
        String sql = """
                SELECT
                    p.cpf,
                    p.nome,
                    d.especialidade,
                    d.cro,
                    COUNT(c.idConsulta) AS totalConsultas
                FROM pessoa p
                JOIN dentista d
                    ON p.cpf = d.cpf
                JOIN consulta c
                    ON d.cpf = c.cpfDentista
                GROUP BY
                    p.cpf, p.nome, d.especialidade, d.cro
                HAVING
                    COUNT(c.idConsulta) >= ?
                ORDER BY
                    totalConsultas DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DentistaAtivoPorConsultasDTO dto = new DentistaAtivoPorConsultasDTO();
            dto.setCpf(rs.getString("cpf"));
            dto.setNome(rs.getString("nome"));
            dto.setEspecialidade(rs.getString("especialidade"));
            dto.setCro(rs.getString("cro"));
            dto.setTotalConsultas(rs.getInt("totalConsultas"));
            return dto;
        }, minConsultas);
    }

    public List<Dentista> buscarDentistasSemConsultaFutura() {
        String sql = "SELECT * FROM vw_dentistas_sem_consulta_futura ORDER BY nome ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Dentista d = new Dentista();
            d.setCpf(rs.getString("cpf"));
            d.setNome(rs.getString("nome"));
            d.setEspecialidade(rs.getString("especialidade"));
            d.setCro(rs.getString("cro"));
            d.setEmail(rs.getString("email"));
            d.setCoordenador(rs.getString("coordenador"));
            Date dataSql = rs.getDate("data_nascimento");
            if (dataSql != null) d.setDataNascimento(new Date(dataSql.getTime()));
            return d;
        });
    }


}