package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Pessoa;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserir(Pessoa p) {
        String sql = "INSERT INTO Pessoa (nome, cpf, cep, bairro, numero, data_nascimento) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                p.getNome(),
                p.getCpf(),
                p.getCep(),
                p.getBairro(),
                p.getNumero(),
                new java.sql.Date(p.getDataNascimento().getTime()));
    }

    public List<Pessoa> listar() {
        String sql = "SELECT * FROM Pessoa";

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

            return p;
        });
    }

    public Pessoa buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Pessoa WHERE cpf = ?";

        List<Pessoa> lista = jdbcTemplate.query(sql, new Object[] { cpf }, (r, i) -> {
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

            return p;
        });

        if (lista.isEmpty()) {
            return null; // ou lançar uma exceção customizada
        }

        return lista.get(0);

    }

    public void deletar(String Cpf) {
        String sql = "DELETE FROM Pessoa WHERE cpf = ?";
        jdbcTemplate.update(sql, Cpf);
    }

    public void atualizar(String Cpf, Pessoa p) {
        String sql = "UPDATE Pessoa SET nome = ?, cpf = ?, cep = ?, bairro = ?, numero = ?, data_nascimento = ? WHERE cpf = ?";

        jdbcTemplate.update(sql,
                p.getNome(),
                p.getCpf(),
                p.getCep(),
                p.getBairro(),
                p.getNumero(),
                new java.sql.Date(p.getDataNascimento().getTime()),
                Cpf);
    }
}
