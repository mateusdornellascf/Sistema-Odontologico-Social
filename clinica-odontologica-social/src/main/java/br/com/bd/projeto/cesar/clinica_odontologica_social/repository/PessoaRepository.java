package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Pessoa;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void inserir(Pessoa p) {
        String sqlPessoa = "INSERT INTO pessoa (cpf, nome, cep, bairro, numero, data_nascimento) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlPessoa,
                p.getCpf(),
                p.getNome(),
                p.getCep(),
                p.getBairro(),
                p.getNumero(),
                new java.sql.Date(p.getDataNascimento().getTime()));

        if (p.getTelefones() != null && !p.getTelefones().isEmpty()) {

            String sqlTelefone = "INSERT INTO telefone (cpf, telefone) VALUES (?, ?)";

            for (String tel : p.getTelefones()) {
                jdbcTemplate.update(sqlTelefone,
                        p.getCpf(),
                        tel);
            }
        }

    }

    public List<Pessoa> listar() {
        String sql = "SELECT * FROM pessoa";

        return jdbcTemplate.query(sql, (r, i) -> {
            Pessoa p = new Pessoa();

            p.setNome(r.getString("nome"));
            p.setCpf(r.getString("cpf"));
            p.setCep(r.getString("cep"));
            p.setBairro(r.getString("bairro"));
            p.setNumero(r.getString("numero"));

            java.sql.Date dataSql = r.getDate("data_nascimento");
            if (dataSql != null) {
                p.setDataNascimento(new Date(dataSql.getTime()));
            }
            String sqlTel = "SELECT telefone FROM telefone WHERE cpf = ?";

            List<String> telefones = jdbcTemplate.query(
                    sqlTel,
                    (rs, rowNum) -> rs.getString("telefone"),
                    p.getCpf());

            p.setTelefones(telefones);

            return p;
        });
    }

    public Pessoa buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Pessoa WHERE cpf = ?";

        List<Pessoa> lista = jdbcTemplate.query(sql, (r, i) -> {
            Pessoa p = new Pessoa();

            p.setNome(r.getString("nome"));
            p.setCpf(r.getString("cpf"));
            p.setCep(r.getString("cep"));
            p.setBairro(r.getString("bairro"));
            p.setNumero(r.getString("numero"));

            java.sql.Date dataSql = r.getDate("data_nascimento");
            if (dataSql != null) {
                p.setDataNascimento(new Date(dataSql.getTime()));
            }
            String sqlTel = "SELECT telefone FROM telefone WHERE cpf = ?";

            List<String> telefones = jdbcTemplate.query(
                    sqlTel,
                    (rs, rowNum) -> rs.getString("telefone"),
                    p.getCpf());

            p.setTelefones(telefones);

            return p;
        }, cpf);

        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    public void deletar(String cpf) {
        try {
            String sql = "DELETE FROM pessoa WHERE cpf = ?";
            jdbcTemplate.update(sql, cpf);

        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException(
                    "Não foi possível deletar a pessoa, pois ela está vinculada a outros registros.");
        }
    }

    @Transactional
    public void atualizar(String cpfAntigo, Pessoa p) {

        String sqlPessoa = "UPDATE pessoa SET nome = ?, cpf = ?, cep = ?, bairro = ?, numero = ?, data_nascimento = ? WHERE cpf = ?";

        jdbcTemplate.update(sqlPessoa,
                p.getNome(),
                p.getCpf(),
                p.getCep(),
                p.getBairro(),
                p.getNumero(),
                new java.sql.Date(p.getDataNascimento().getTime()),
                cpfAntigo);

        String deleteTel = "DELETE FROM telefone WHERE cpf = ?";
        jdbcTemplate.update(deleteTel, cpfAntigo);

        // 🔹 3. Inserir novos telefones
        if (p.getTelefones() != null && !p.getTelefones().isEmpty()) {

            String insertTel = "INSERT INTO telefone (cpf, telefone) VALUES (?, ?)";

            for (String tel : p.getTelefones()) {
                jdbcTemplate.update(insertTel,
                        p.getCpf(),
                        tel);
            }
        }
    }

    public boolean existe(String cpf) {
        String sql = "SELECT COUNT(*) FROM pessoa WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }
}
