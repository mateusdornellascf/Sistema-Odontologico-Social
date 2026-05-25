package br.com.bd.projeto.cesar.clinica_odontologica_social.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbc;

    public DashboardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int count(String tabela) {
        Integer v = jdbc.queryForObject("SELECT COUNT(*) FROM " + tabela, Integer.class);
        return v == null ? 0 : v;
    }

    public int pacientesComFormulario() {
        Integer v = jdbc.queryForObject(
                "SELECT COUNT(DISTINCT cpfPaciente) FROM formulariosaude", Integer.class);
        return v == null ? 0 : v;
    }

    public Double ticketMedioCirurgico()  { return mediaValor("cirurgico"); }
    public Double ticketMedioEstetico()   { return mediaValor("estetico"); }
    public Double ticketMedioRotina()     { return mediaValor("rotina"); }

    private Double mediaValor(String tabela) {
        return jdbc.queryForObject("SELECT AVG(valor) FROM " + tabela, Double.class);
    }

    public Double valorTotalGeral() {
        Double v = jdbc.queryForObject("""
                SELECT
                    COALESCE((SELECT SUM(valor) FROM cirurgico),0)
                  + COALESCE((SELECT SUM(valor) FROM estetico),0)
                  + COALESCE((SELECT SUM(valor) FROM rotina),0) AS total
                """, Double.class);
        return v == null ? 0.0 : v;
    }

    public List<Map<String, Object>> consultasPorMes(Integer ano) {
        StringBuilder sql = new StringBuilder("""
                SELECT DATE_FORMAT(dataConsulta, '%Y-%m') AS mes,
                       COUNT(*) AS total
                FROM consulta
                """);
        List<Object> params = new ArrayList<>();
        if (ano != null) {
            sql.append(" WHERE YEAR(dataConsulta) = ? ");
            params.add(ano);
        }
        sql.append(" GROUP BY mes ORDER BY mes ");

        return jdbc.query(sql.toString(), (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("mes", rs.getString("mes"));
            m.put("total", rs.getInt("total"));
            return m;
        }, params.toArray());
    }


    public List<Map<String, Object>> consultasPorDentista(int limit) {
        String sql = """
                SELECT p.nome, COUNT(c.idConsulta) AS total
                FROM dentista d
                JOIN pessoa p ON d.cpf = p.cpf
                LEFT JOIN consulta c ON c.cpfDentista = d.cpf
                GROUP BY d.cpf, p.nome
                ORDER BY total DESC
                LIMIT ?
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("nome", rs.getString("nome"));
            m.put("total", rs.getInt("total"));
            return m;
        }, limit);
    }

    public List<Map<String, Object>> distribuicaoRisco() {
        String sql = """
                SELECT classificacao, COUNT(*) AS total
                FROM (
                    SELECT pa.cpf,
                           CASE
                               WHEN f.idFormulario IS NULL THEN 'SEM FORMULARIO'
                               WHEN (
                                   (CASE WHEN f.alergia IS NOT NULL AND TRIM(f.alergia) <> ''
                                              AND LOWER(TRIM(f.alergia)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                                 + (CASE WHEN f.medicamento IS NOT NULL AND TRIM(f.medicamento) <> ''
                                              AND LOWER(TRIM(f.medicamento)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                                 + (CASE WHEN f.doencas IS NOT NULL AND TRIM(f.doencas) <> ''
                                              AND LOWER(TRIM(f.doencas)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                               ) = 0 THEN 'SEM RISCO'
                               WHEN (
                                   (CASE WHEN f.alergia IS NOT NULL AND TRIM(f.alergia) <> ''
                                              AND LOWER(TRIM(f.alergia)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                                 + (CASE WHEN f.medicamento IS NOT NULL AND TRIM(f.medicamento) <> ''
                                              AND LOWER(TRIM(f.medicamento)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                                 + (CASE WHEN f.doencas IS NOT NULL AND TRIM(f.doencas) <> ''
                                              AND LOWER(TRIM(f.doencas)) NOT IN ('nenhum','nenhuma','nao','n/a')
                                         THEN 1 ELSE 0 END)
                               ) = 1 THEN 'ATENCAO'
                               ELSE 'ALTO RISCO'
                           END AS classificacao
                    FROM paciente pa
                    LEFT JOIN formulariosaude f
                           ON f.cpfPaciente = pa.cpf
                          AND f.idFormulario = (
                               SELECT MAX(f2.idFormulario)
                               FROM formulariosaude f2
                               WHERE f2.cpfPaciente = pa.cpf
                          )
                ) t
                GROUP BY classificacao
                ORDER BY total DESC
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("classificacao", rs.getString("classificacao"));
            m.put("total", rs.getInt("total"));
            return m;
        });
    }


    public List<Map<String, Object>> procedimentosPorTipo() {
        String sql = """
                SELECT 'CIRURGICO' AS tipo, COUNT(*) AS total, AVG(valor) AS valorMedio
                FROM cirurgico
                UNION ALL
                SELECT 'ESTETICO',  COUNT(*), AVG(valor) FROM estetico
                UNION ALL
                SELECT 'ROTINA',    COUNT(*), AVG(valor) FROM rotina
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("tipo", rs.getString("tipo"));
            m.put("total", rs.getInt("total"));
            double vm = rs.getDouble("valorMedio");
            m.put("valorMedio", rs.wasNull() ? 0.0 : vm);
            return m;
        });
    }

    public List<Map<String, Object>> consultasPorEspecialidade() {
        String sql = """
                SELECT COALESCE(d.especialidade,'(sem)') AS especialidade,
                       COUNT(c.idConsulta) AS total
                FROM dentista d
                LEFT JOIN consulta c ON c.cpfDentista = d.cpf
                GROUP BY d.especialidade
                ORDER BY total DESC
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("especialidade", rs.getString("especialidade"));
            m.put("total", rs.getInt("total"));
            return m;
        });
    }

    public List<Integer> idadesDosPacientes() {
        String sql = """
                SELECT TIMESTAMPDIFF(YEAR, pe.data_nascimento, CURDATE()) AS idade
                FROM paciente pa
                JOIN pessoa pe ON pe.cpf = pa.cpf
                WHERE pe.data_nascimento IS NOT NULL
                """;
        return jdbc.query(sql, (rs, i) -> {
            int v = rs.getInt("idade");
            return rs.wasNull() ? null : v;
        }).stream().filter(java.util.Objects::nonNull).toList();
    }

    public List<Map<String, Object>> pacientesPorBairro() {
        String sql = """
                SELECT COALESCE(pe.bairro,'(sem)') AS bairro, COUNT(*) AS total
                FROM paciente pa
                JOIN pessoa pe ON pe.cpf = pa.cpf
                GROUP BY pe.bairro
                ORDER BY total DESC
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("bairro", rs.getString("bairro"));
            m.put("total", rs.getInt("total"));
            return m;
        });
    }

    public List<Map<String, Object>> consultasVsProcedimentosPorDentista() {
        String sql = """
                SELECT p.nome,
                       COUNT(DISTINCT c.idConsulta) AS totalConsultas,
                       COUNT(pr.idProcedimento)     AS totalProcedimentos
                FROM dentista d
                JOIN pessoa p ON p.cpf = d.cpf
                LEFT JOIN consulta c     ON c.cpfDentista = d.cpf
                LEFT JOIN procedimento pr ON pr.idConsulta = c.idConsulta
                GROUP BY d.cpf, p.nome
                ORDER BY totalConsultas DESC
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("nome", rs.getString("nome"));
            m.put("totalConsultas", rs.getInt("totalConsultas"));
            m.put("totalProcedimentos", rs.getInt("totalProcedimentos"));
            return m;
        });
    }


    public Map<String, Object> estatisticasIdade() {
        List<Integer> idades = idadesDosPacientes();
        Map<String, Object> out = new HashMap<>();
        out.put("amostra", idades.size());

        if (idades.isEmpty()) {
            out.put("media", 0);
            out.put("mediana", 0);
            out.put("moda", 0);
            out.put("variancia", 0);
            out.put("desvio", 0);
            out.put("min", 0);
            out.put("max", 0);
            out.put("histograma", List.of());
            return out;
        }

        double media = idades.stream().mapToInt(Integer::intValue).average().orElse(0);
        List<Integer> ord = idades.stream().sorted().toList();
        double mediana;
        int n = ord.size();
        if (n % 2 == 1) mediana = ord.get(n / 2);
        else mediana = (ord.get(n / 2 - 1) + ord.get(n / 2)) / 2.0;

        Map<Integer, Long> freq = new HashMap<>();
        for (int v : idades) freq.merge(v, 1L, Long::sum);
        int moda = freq.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .get().getKey();

        double variancia = idades.stream()
                .mapToDouble(v -> Math.pow(v - media, 2))
                .average().orElse(0);
        double desvio = Math.sqrt(variancia);

        int min = ord.get(0);
        int max = ord.get(n - 1);

        Map<String, Integer> hist = new java.util.LinkedHashMap<>();
        int faixaInicio = (min / 10) * 10;
        int faixaFim    = ((max / 10) + 1) * 10;
        for (int f = faixaInicio; f < faixaFim; f += 10) {
            hist.put(f + "-" + (f + 9), 0);
        }
        for (int v : idades) {
            int base = (v / 10) * 10;
            String chave = base + "-" + (base + 9);
            hist.merge(chave, 1, Integer::sum);
        }

        List<Map<String, Object>> histograma = new ArrayList<>();
        hist.forEach((k, v) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("faixa", k);
            m.put("total", v);
            histograma.add(m);
        });

        out.put("media", round2(media));
        out.put("mediana", round2(mediana));
        out.put("moda", moda);
        out.put("variancia", round2(variancia));
        out.put("desvio", round2(desvio));
        out.put("min", min);
        out.put("max", max);
        out.put("histograma", histograma);
        return out;
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
