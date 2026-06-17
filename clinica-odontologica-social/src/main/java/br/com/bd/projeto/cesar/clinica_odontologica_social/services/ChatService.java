package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final JdbcTemplate jdbcTemplate;
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    public ChatService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    public String perguntar(String pergunta) {
        try {
            String sql = gerarSqlComGemini(pergunta);

            if (sql.equalsIgnoreCase("FORA_DO_CONTEXTO")) {
                return "Só posso responder perguntas relacionadas aos dados do Sistema Odontológico Social.";
            }

            if (!sqlValido(sql)) {
                return "Não consegui gerar uma consulta segura para essa pergunta. Tente perguntar de outra forma.";
            }

            List<Map<String, Object>> resultado = jdbcTemplate.queryForList(sql);

            return gerarRespostaComGemini(pergunta, sql, resultado);

        } catch (Exception e) {
            return "Ocorreu um erro ao consultar a IA ou o banco de dados: " + e.getMessage();
        }
    }

    private String gerarSqlComGemini(String pergunta) {

        String prompt = """
                Você é um assistente de dados de um Sistema Odontológico Social.

                Sua tarefa é transformar a pergunta do usuário em UMA consulta SQL MySQL.

                REGRAS OBRIGATÓRIAS:
                - Responda SOMENTE com SQL ou com FORA_DO_CONTEXTO.
                - Use apenas SELECT.
                - Se a pergunta NÃO for sobre dados do Sistema Odontológico Social, responda exatamente: FORA_DO_CONTEXTO.
                - Não responda perguntas de conhecimento geral.
                - Não responda perguntas sobre programação, história, geografia, matemática ou qualquer assunto fora do sistema.
                - Se a pergunta não puder ser respondida usando as tabelas disponíveis, responda exatamente: FORA_DO_CONTEXTO.
                - Não use INSERT, UPDATE, DELETE, DROP, ALTER, CREATE, TRUNCATE, GRANT ou REVOKE.
                - Não use markdown.
                - Não coloque ```sql.
                - Não invente tabelas.
                - Não invente colunas.
                - Use apenas as tabelas listadas abaixo.

                TABELAS DISPONÍVEIS:

                pessoa(cpf, nome, data_nascimento, cep, rua, bairro, numero)

                telefone(cpf, telefone)

                paciente(cpf, numPlanoSaude)

                dentista(cpf, cro, especialidade, email, coordenador)

                formulariosaude(idFormulario, cpfPaciente, alergia, medicamento, doencas)

                consulta(idConsulta, cpfPaciente, cpfDentista, dataConsulta, horaConsulta)

                procedimento(idProcedimento, idConsulta, nomeProcedimento, descricao)

                cirurgico(idProcedimento, dataCirurgia, cpfCirurgiaoDentista, valor)

                estetico(idProcedimento, quantidadeSessoes, dataSessoes, valor)

                rotina(idProcedimento, dataProcedimentoRotina, statusProcedimento, valor)

                vw_pacientes_com_alerta_saude(cpf, nome, idConsulta, dataConsulta, horaConsulta, alergia, doencas, medicamento)

                vw_dentistas_sem_consulta_futura(cpf, nome, data_nascimento, especialidade, cro, email, coordenador)

                EXEMPLOS:

                Pergunta: Quantos pacientes estão cadastrados?
                SQL: SELECT COUNT(*) AS total_pacientes FROM paciente;

                Pergunta: Qual é o faturamento total?
                SQL: SELECT COALESCE((SELECT SUM(valor) FROM cirurgico), 0) + COALESCE((SELECT SUM(valor) FROM estetico), 0) + COALESCE((SELECT SUM(valor) FROM rotina), 0) AS faturamento_total;

                Pergunta: Quais são os top dentistas por nº de consultas?
                SQL: SELECT pe.nome, d.cro, d.especialidade, COUNT(c.idConsulta) AS total_consultas FROM dentista d JOIN pessoa pe ON pe.cpf = d.cpf LEFT JOIN consulta c ON c.cpfDentista = d.cpf GROUP BY pe.nome, d.cro, d.especialidade ORDER BY total_consultas DESC;

                Pergunta: Existe paciente chamado João?
                SQL: SELECT pe.cpf, pe.nome FROM pessoa pe JOIN paciente pa ON pe.cpf = pa.cpf WHERE LOWER(pe.nome) LIKE '%joão%' OR LOWER(pe.nome) LIKE '%joao%';

                Pergunta: Quais pacientes têm alergia?
                SQL: SELECT nome, idConsulta, dataConsulta, horaConsulta, alergia FROM vw_pacientes_com_alerta_saude WHERE alergia IS NOT NULL AND alergia <> '';

                Pergunta: Quais dentistas estão sem consulta futura?
                SQL: SELECT nome, especialidade, cro, email FROM vw_dentistas_sem_consulta_futura;

                Pergunta do usuário:
                """ + pergunta;

        return limparSql(chamarGemini(prompt));
    }

    private String gerarRespostaComGemini(String pergunta, String sql, List<Map<String, Object>> resultado) {

        String prompt = """
                Você é um assistente de um Sistema Odontológico Social.

                Responda em português, de forma clara, objetiva e natural.
                Não use Markdown, asteriscos, listas com marcadores ou aspas decorativas.
                Escreva em texto simples, pois a resposta será exibida diretamente na tela.

                Pergunta do usuário:
                """ + pergunta + """

                SQL executado:
                """ + sql + """

                Resultado retornado pelo banco:
                """ + resultado + """

                Explique a resposta com base apenas nesses dados.
                Se o resultado estiver vazio, diga que não encontrou registros.
                """;

        return limparResposta(chamarGemini(prompt));
    }

    private String chamarGemini(String prompt) {

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        Map response = webClient.post()
                .uri("/v1beta/models/" + model + ":generateContent?key=" + apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List candidates = (List) response.get("candidates");

        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("Não foi possível obter resposta da IA. Tente novamente em alguns momentos.");
        }

        Map candidate = (Map) candidates.get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");
        Map part = (Map) parts.get(0);

        return part.get("text").toString();
    }

    private String limparSql(String sql) {
        return sql
                .replace("```sql", "")
                .replace("```", "")
                .trim();
    }

    private String limparResposta(String resposta) {
        return resposta
                .replace("**", "")
                .replace("* ", "")
                .trim();
    }

    private boolean sqlValido(String sql) {

        String s = sql.trim().toLowerCase();

        if (!s.startsWith("select")) {
            return false;
        }

        String[] proibidos = {
                "insert", "update", "delete", "drop", "alter",
                "create", "truncate", "replace", "grant", "revoke"
        };

        for (String palavra : proibidos) {
            if (s.contains(palavra)) {
                return false;
            }
        }

        return true;
    }
}
