package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FormularioSaudeRepository {
    
    private final JdbcTemplate jdbcTemplate;
    public FormularioSaudeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void listar() {
        jdbcTemplate.query("SELECT * FROM formulario_saude", (rs, rowNum) -> {
            int idFormulario = rs.getInt("id_formulario");
            String identificacaoPaciente = rs.getString("identificacao_paciente");
            String alergias = rs.getString("alergias");
            String doencas = rs.getString("doencas");
            String medicamentos = rs.getString("medicamentos");
            
            System.out.println("ID: " + idFormulario);
            System.out.println("Identificação do Paciente: " + identificacaoPaciente);
            System.out.println("Alergias: " + alergias);
            System.out.println("Doenças: " + doencas);
            System.out.println("Medicamentos: " + medicamentos);
            System.out.println("-----------------------------");
            
            return null; 
        });
    }

    public void buscarPorCpf(String cpf) {
        jdbcTemplate.query("SELECT * FROM formulario_saude WHERE cpfPaciente = ?", new Object[]{cpf}, (rs, rowNum) -> {
            int idFormulario = rs.getInt("id_formulario");
            String identificacaoPaciente = rs.getString("cpfPaciente");
            String alergias = rs.getString("alergias");
            String doencas = rs.getString("doencas");
            String medicamentos = rs.getString("medicamentos");

            System.out.println("ID: " + idFormulario);
            System.out.println("Identificação do Paciente: " + identificacaoPaciente);
            System.out.println("Alergias: " + alergias);
            System.out.println("Doenças: " + doencas);
            System.out.println("Medicamentos: " + medicamentos);
            System.out.println("-----------------------------");

            return null;
        });
    }

}
