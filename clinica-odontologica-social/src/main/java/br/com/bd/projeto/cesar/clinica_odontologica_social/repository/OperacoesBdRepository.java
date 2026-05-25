package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.stereotype.Repository;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.AlertaAtendimentoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.LogConsultaDTO;

@Repository
public class OperacoesBdRepository {

    private final JdbcTemplate jdbcTemplate;

    public OperacoesBdRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer calcularIdadePessoa(String cpf) {
        String sql = "SELECT func_calcular_idade_pessoa(?) AS idade";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> {
            int v = rs.getInt("idade");
            return rs.wasNull() ? null : v;
        }, cpf);
    }

    public String classificarRiscoSaude(String cpf) {
        String sql = "SELECT func_classificar_risco_saude(?) AS risco";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> rs.getString("risco"), cpf);
    }

    public int gerarAlertasConsultasDoDia(LocalDate data) {
        jdbcTemplate.update("CALL sp_gerar_alertas_consultas_do_dia(?)", data);

        String sql = """
                SELECT COUNT(*) AS total
                FROM alerta_atendimento aa
                JOIN consulta c ON aa.idconsulta = c.idconsulta
                WHERE c.dataconsulta = ?
                """;
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, data);
        return total == null ? 0 : total;
    }

    public String chamarSpRemarcarConsulta(int idConsulta, LocalDate novaData, java.time.LocalTime novaHora) {
        Map<String, Object> out = jdbcTemplate.call(con -> {
            var cs = con.prepareCall("{call sp_remarcar_consulta(?, ?, ?)}");
            cs.setInt(1, idConsulta);
            cs.setDate(2, java.sql.Date.valueOf(novaData));
            cs.setTime(3, java.sql.Time.valueOf(novaHora));
            return cs;
        }, List.of(
                new SqlParameter("p_id_consulta", java.sql.Types.INTEGER),
                new SqlParameter("p_nova_data", java.sql.Types.DATE),
                new SqlParameter("p_nova_hora", java.sql.Types.TIME),
                new SqlReturnResultSet("result", (rs, i) -> rs.getString("mensagem"))));

        Object lista = out.get("result");
        if (lista instanceof List<?> l && !l.isEmpty()) {
            return String.valueOf(l.get(0));
        }
        return "procedure executada";
    }

    public List<AlertaAtendimentoDTO> listarAlertas(LocalDate data) {
        StringBuilder sql = new StringBuilder("""
                SELECT aa.idalerta, aa.idconsulta, aa.cpfpaciente, aa.cpfdentista,
                       aa.classificacaorisco, aa.mensagem, aa.datageracao
                FROM alerta_atendimento aa
                """);
        List<Object> params = new java.util.ArrayList<>();
        if (data != null) {
            sql.append(" JOIN consulta c ON aa.idconsulta = c.idconsulta ");
            sql.append(" WHERE c.dataconsulta = ? ");
            params.add(data);
        }
        sql.append(" ORDER BY aa.datageracao DESC ");

        return jdbcTemplate.query(sql.toString(), (rs, i) -> {
            AlertaAtendimentoDTO dto = new AlertaAtendimentoDTO();
            dto.setIdAlerta(rs.getInt("idalerta"));
            dto.setIdConsulta(rs.getInt("idconsulta"));
            dto.setCpfPaciente(rs.getString("cpfpaciente"));
            dto.setCpfDentista(rs.getString("cpfdentista"));
            dto.setClassificacaoRisco(rs.getString("classificacaorisco"));
            dto.setMensagem(rs.getString("mensagem"));
            var ts = rs.getTimestamp("datageracao");
            if (ts != null) dto.setDataGeracao(ts.toLocalDateTime());
            return dto;
        }, params.toArray());
    }

    public List<LogConsultaDTO> listarLogConsulta(Integer idConsulta) {
        StringBuilder sql = new StringBuilder("""
                SELECT idlog, idconsulta, cpfpaciente, cpfdentista,
                       dataantiga, horaantiga, datanova, horanova,
                       operacao, datalog
                FROM log_consulta
                """);
        List<Object> params = new java.util.ArrayList<>();
        if (idConsulta != null) {
            sql.append(" WHERE idconsulta = ? ");
            params.add(idConsulta);
        }
        sql.append(" ORDER BY datalog DESC ");

        return jdbcTemplate.query(sql.toString(), (rs, i) -> {
            LogConsultaDTO dto = new LogConsultaDTO();
            dto.setIdLog(rs.getInt("idlog"));
            dto.setIdConsulta(rs.getInt("idconsulta"));
            dto.setCpfPaciente(rs.getString("cpfpaciente"));
            dto.setCpfDentista(rs.getString("cpfdentista"));
            java.sql.Date d1 = rs.getDate("dataantiga");
            if (d1 != null) dto.setDataAntiga(d1.toLocalDate());
            java.sql.Time t1 = rs.getTime("horaantiga");
            if (t1 != null) dto.setHoraAntiga(t1.toLocalTime());
            java.sql.Date d2 = rs.getDate("datanova");
            if (d2 != null) dto.setDataNova(d2.toLocalDate());
            java.sql.Time t2 = rs.getTime("horanova");
            if (t2 != null) dto.setHoraNova(t2.toLocalTime());
            dto.setOperacao(rs.getString("operacao"));
            var ts = rs.getTimestamp("datalog");
            if (ts != null) dto.setDataLog(ts.toLocalDateTime());
            return dto;
        }, params.toArray());
    }
}
