package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Procedimento;

@Repository
public class ProcedimentoRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProcedimentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long criarProcedimento(Procedimento p) {

        String sql = "INSERT INTO procedimento (idConsulta, nomeProcedimento, descricao) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                p.getIdConsulta(),
                p.getNomeProcedimento(),
                p.getDescricao());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public void criarCirurgico(Long idProcedimento,
            String dataCirurgia,
            String cpfCirurgiao,
            Double valor) {

        String sql = """
                    INSERT INTO cirurgico
                    (idProcedimento, dataCirurgia, cpfCirurgiaoDentista, valor)
                    VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                idProcedimento,
                dataCirurgia,
                cpfCirurgiao,
                valor);
    }

    public void criarEstetico(Long idProcedimento,
            Integer quantidadeSessoes,
            String dataSessoes,
            Double valor) {

        String sql = """
                    INSERT INTO estetico
                    (idProcedimento, quantidadeSessoes, dataSessoes, valor)
                    VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                idProcedimento,
                quantidadeSessoes,
                dataSessoes,
                valor);
    }

    public void criarRotina(Long idProcedimento,
            String dataProcedimento,
            String status,
            Double valor) {

        String sql = """
                    INSERT INTO rotina
                    (idProcedimento, dataProcedimentoRotina, statusProcedimento, valor)
                    VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                idProcedimento,
                dataProcedimento,
                status,
                valor);
    }

    public List<Procedimento> listar() {
        String sql = "SELECT * FROM procedimento";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Procedimento p = new Procedimento();
            p.setIdProcedimento(rs.getLong("idProcedimento"));
            p.setIdConsulta(rs.getLong("idConsulta"));
            p.setNomeProcedimento(rs.getString("nomeProcedimento"));
            p.setDescricao(rs.getString("descricao"));
            return p;
        });
    }

    public boolean atualizar(Procedimento p) {
        String sql = "UPDATE procedimento SET nomeProcedimento = ?, descricao = ? WHERE idProcedimento = ?";

        int linhasAfetadas = jdbcTemplate.update(sql,
                p.getNomeProcedimento(),
                p.getDescricao(),
                p.getIdProcedimento());
                System.out.println("Linhas afetadas: " + linhasAfetadas);
        return linhasAfetadas > 0;
    }

    public boolean deletar(Long idProcedimento) {

        String sql = "DELETE FROM procedimento WHERE idProcedimento = ?";

        int linhas = jdbcTemplate.update(sql, idProcedimento);

        return linhas > 0;
    }

    public List<Procedimento> buscarPorIdConsulta(Long idConsulta) {
        String sql = "SELECT * FROM procedimento WHERE idConsulta = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Procedimento p = new Procedimento();
            p.setIdProcedimento(rs.getLong("idProcedimento"));
            p.setIdConsulta(rs.getLong("idConsulta"));
            p.setNomeProcedimento(rs.getString("nomeProcedimento"));
            p.setDescricao(rs.getString("descricao"));
            return p;
        }, idConsulta);
    }

    public Procedimento buscarPorIdProcedimento(Long idProcedimento) {

        String sql = "SELECT * FROM procedimento WHERE idProcedimento = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Procedimento p = new Procedimento();
            p.setIdProcedimento(rs.getLong("idProcedimento"));
            p.setIdConsulta(rs.getLong("idConsulta"));
            p.setNomeProcedimento(rs.getString("nomeProcedimento"));
            p.setDescricao(rs.getString("descricao"));
            return p;
        }, idProcedimento);
    }

}
