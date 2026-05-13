-- ============================================================
-- CONSULTAS SQL — Sistema Odontológico Social
-- ============================================================
use db_clinica_odontologica;

-- ============================================================
-- ConsultaRepository
-- ============================================================

-- Verifica se já existe consulta para o dentista no horário informado
-- Método: existeConsultaNoHorario()
SELECT COUNT(*)
FROM consulta
WHERE cpfDentista  = ?
  AND dataConsulta = ?
  AND horaConsulta = ?;

-- Verifica se uma consulta existe pelo ID (int) — usada antes de remarcar
-- Método: verificarConsultaExiste()
SELECT COUNT(*)
FROM consulta
WHERE idConsulta = ?;

-- Verifica se um paciente existe antes de agendar uma consulta
-- Método: existePaciente()
SELECT COUNT(*)
FROM paciente
WHERE cpf = ?;

-- Verifica se um dentista existe antes de agendar uma consulta
-- Método: existeDentista()
SELECT COUNT(*)
FROM dentista
WHERE cpf = ?;

-- Lista todas as consultas cadastradas
-- Método: listar()
SELECT *
FROM consulta;

-- Lista consultas de um paciente
-- Método: listarPorPaciente()
SELECT *
FROM consulta
WHERE cpfPaciente = ?;

-- Lista consultas de um dentista
-- Método: listarPorDentista()
SELECT *
FROM consulta
WHERE cpfDentista = ?;

-- Busca uma consulta pelo ID
-- Método: buscarPorId()
SELECT *
FROM consulta
WHERE idConsulta = ?;

-- Verifica se existem procedimentos vinculados a uma consulta (impede deleção)
-- Método: existeProcedimentos()
SELECT COUNT(*)
FROM procedimento
WHERE idConsulta = ?;

-- Lista todas as consultas de um dentista específico (endpoint do controller)
-- Método: listarConsultasDentista()
SELECT *
FROM consulta
WHERE cpfDentista = ?;

-- Lista todas as consultas de um paciente específico (endpoint do controller)
-- Método: listarConsultasPaciente()
SELECT *
FROM consulta
WHERE cpfPaciente = ?;

-- Verifica se uma consulta existe pelo ID (Long) — usada antes de criar procedimento
-- Método: existeConsulta()
SELECT COUNT(*)
FROM consulta
WHERE idConsulta = ?;


-- ============================================================
-- DentistaRepository
-- ============================================================

-- Lista todos os dentistas com dados completos
-- Método: listar()
SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf;

-- Busca um dentista pelo CPF com dados completos
-- Método: buscarPorCpf()
SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf
WHERE p.cpf = ?;

-- Verifica se o dentista possui consultas vinculadas (impede deleção)
-- Método: temConsulta()
SELECT COUNT(*)
FROM consulta
WHERE cpfDentista = ?;

-- Verifica se um dentista existe pelo CPF
-- Método: existe()
SELECT COUNT(*)
FROM dentista
WHERE cpf = ?;

-- Busca os telefones de um dentista
-- Método: buscarTelefones()
SELECT telefone
FROM telefone
WHERE cpf = ?;


-- ============================================================
-- FormularioSaudeRepository
-- ============================================================

-- Lista todos os formulários de saúde
-- Método: listar()
SELECT *
FROM formulariosaude;

-- Busca o formulário de saúde de um paciente pelo CPF
-- Método: buscarPorCpf()
SELECT *
FROM formulariosaude
WHERE cpfPaciente = ?;

-- Verifica se já existe formulário de saúde para o paciente
-- Método: existe()
SELECT COUNT(*)
FROM formulariosaude
WHERE cpfPaciente = ?;


-- ============================================================
-- PacienteRepository
-- ============================================================

-- Lista todos os pacientes com dados completos
-- Método: listar()
SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf;

-- Busca um paciente pelo CPF com dados completos
-- Método: buscarPorCpf()
SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf
WHERE p.cpf = ?;

-- Verifica se um paciente existe pelo CPF
-- Método: existe()
SELECT COUNT(*)
FROM paciente
WHERE cpf = ?;

-- Busca os telefones de um paciente
-- Método: buscarTelefones()
SELECT telefone
FROM telefone
WHERE cpf = ?;

-- Verifica se o paciente possui consultas vinculadas (impede deleção)
-- Método: temConsulta()
SELECT COUNT(*)
FROM consulta
WHERE cpfPaciente = ?;


-- ============================================================
-- PessoaRepository
-- ============================================================

-- Lista todas as pessoas cadastradas
-- Método: listar()
SELECT *
FROM pessoa;

-- Busca telefones de uma pessoa — chamada interna dentro de listar()
-- Método: listar()
SELECT telefone
FROM telefone
WHERE cpf = ?;

-- Busca uma pessoa pelo CPF
-- Método: buscarPorCpf()
SELECT *
FROM pessoa
WHERE cpf = ?;

-- Busca telefones de uma pessoa — chamada interna dentro de buscarPorCpf()
-- Método: buscarPorCpf()
SELECT telefone
FROM telefone
WHERE cpf = ?;

-- Verifica se uma pessoa existe pelo CPF
-- Método: existe()
SELECT COUNT(*)
FROM pessoa
WHERE cpf = ?;


-- ============================================================
-- ProcedimentoRepository
-- ============================================================

-- Recupera o ID do último procedimento inserido
-- Método: criarProcedimento()
SELECT LAST_INSERT_ID();

-- Lista todos os procedimentos
-- Método: listar()
SELECT *
FROM procedimento;

-- Busca todos os procedimentos de uma consulta
-- Método: buscarPorIdConsulta()
SELECT *
FROM procedimento
WHERE idConsulta = ?;

-- Busca um procedimento pelo ID
-- Método: buscarPorIdProcedimento()
SELECT *
FROM procedimento
WHERE idProcedimento = ?;


-- ============================================================
-- RelatorioRepository — leitura das views
-- ============================================================

-- Lê todos os registros da View 1 (vw_pacientes_com_alerta_saude)
-- Método: listarPacientesComAlertaSaude()
SELECT *
FROM vw_pacientes_com_alerta_saude
ORDER BY nome ASC;

-- Lê todos os registros da View 2 (vw_dentistas_sem_consulta_futura)
-- Método: listarDentistasSemConsultaFutura()
SELECT *
FROM vw_dentistas_sem_consulta_futura
ORDER BY nome ASC;


-- ============================================================
-- CONSULTAS ANALÍTICAS (requisito do trabalho)
-- ============================================================

-- ------------------------------------------------------------
-- CONSULTA 1 — JOIN + GROUP BY + HAVING
-- Dentistas que realizaram ao menos N consultas.
-- Útil para identificar os profissionais mais ativos e avaliar
-- a distribuição de carga de trabalho na clínica.
-- Tabelas: pessoa, dentista, consulta
-- Usa índice: idx_consulta_cpfDentista
-- ------------------------------------------------------------
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
    p.cpf,
    p.nome,
    d.especialidade,
    d.cro
HAVING
    COUNT(c.idConsulta) >= ?   -- parâmetro: número mínimo de consultas (ex: 1)
ORDER BY
    totalConsultas DESC;


-- ------------------------------------------------------------
-- CONSULTA 2 — 2 JOINs + WHERE
-- Histórico completo de consultas de um paciente, incluindo
-- nome e especialidade do dentista atendente em cada consulta.
-- Nota: a tabela pessoa é unida duas vezes com aliases
-- distintos — uma para dados do paciente e outra para o dentista.
-- Tabelas: consulta, pessoa (p_pac), pessoa (p_den), dentista
-- ------------------------------------------------------------
SELECT
    c.idConsulta,
    c.cpfPaciente,
    p_pac.nome      AS nomePaciente,
    c.cpfDentista,
    p_den.nome      AS nomeDentista,
    d.especialidade AS especialidadeDentista,
    c.dataConsulta,
    c.horaConsulta
FROM consulta c
JOIN pessoa p_pac
    ON c.cpfPaciente = p_pac.cpf
JOIN pessoa p_den
    ON c.cpfDentista = p_den.cpf
JOIN dentista d
    ON c.cpfDentista = d.cpf
WHERE
    c.cpfPaciente = ?          -- parâmetro: CPF do paciente
ORDER BY
    c.dataConsulta DESC,
    c.horaConsulta DESC;


-- ------------------------------------------------------------
-- CONSULTA 3 — ANTI JOIN (LEFT JOIN + IS NULL)
-- Pacientes cadastrados que nunca agendaram uma consulta.
-- Útil para ações de reengajamento: contato, lembretes, etc.
-- Tabelas: pessoa, paciente, consulta
-- ------------------------------------------------------------
SELECT
    pe.cpf,
    pe.nome,
    pe.bairro,
    pe.cep,
    pe.data_nascimento,
    pa.numPlanoSaude
FROM pessoa pe
JOIN paciente pa
    ON pe.cpf = pa.cpf
LEFT JOIN consulta c
    ON pa.cpf = c.cpfPaciente
WHERE
    c.idConsulta IS NULL       -- mantém apenas quem não tem nenhuma consulta
ORDER BY
    pe.nome ASC;


-- ------------------------------------------------------------
-- CONSULTA 4 — SUBCONSULTA
-- Todos os procedimentos realizados nas consultas do dentista
-- com o maior número de atendimentos registrados no sistema.
-- Tabelas: procedimento, consulta, pessoa
-- Usa índice: idx_consulta_cpfDentista
-- ------------------------------------------------------------
SELECT
    pr.idProcedimento,
    pr.idConsulta,
    pr.nomeProcedimento,
    pr.descricao,
    c.cpfDentista,
    pe.nome AS nomeDentista
FROM procedimento pr
JOIN consulta c
    ON pr.idConsulta = c.idConsulta
JOIN pessoa pe
    ON c.cpfDentista = pe.cpf
WHERE c.cpfDentista = (
    SELECT cpfDentista          -- subconsulta: encontra o dentista com mais consultas
    FROM consulta
    GROUP BY cpfDentista
    ORDER BY COUNT(*) DESC
    LIMIT 1
)
ORDER BY
    pr.idConsulta     ASC,
    pr.idProcedimento ASC;
