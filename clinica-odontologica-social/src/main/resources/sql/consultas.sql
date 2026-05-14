-- ============================================================
-- CONSULTAS SQL - Sistema Odontologico Social
-- ============================================================
USE db_clinica_odontologica;

-- Valores de exemplo usados nas consultas abaixo.
-- Altere estes valores antes de executar o script, se necessario.
SET @cpfPaciente = '12345678901';
SET @cpfDentista = '55566677788';
SET @cpfPessoa = '12345678901';
SET @idConsulta = 1;
SET @idProcedimento = 1;
SET @dataConsulta = '2026-04-01';
SET @horaConsulta = '09:00:00';
SET @minimoConsultas = 1;


-- ============================================================
-- ConsultaRepository
-- ============================================================

-- Verifica se ja existe consulta para o dentista no horario informado
-- Metodo: existeConsultaNoHorario()
SELECT COUNT(*) AS total
FROM consulta
WHERE cpfDentista = @cpfDentista
  AND dataConsulta = @dataConsulta
  AND horaConsulta = @horaConsulta;

-- Verifica se uma consulta existe pelo ID, usada antes de remarcar
-- Metodo: verificarConsultaExiste()
SELECT COUNT(*) AS total
FROM consulta
WHERE idConsulta = @idConsulta;

-- Verifica se um paciente existe antes de agendar uma consulta
-- Metodo: existePaciente()
SELECT COUNT(*) AS total
FROM paciente
WHERE cpf = @cpfPaciente;

-- Verifica se um dentista existe antes de agendar uma consulta
-- Metodo: existeDentista()
SELECT COUNT(*) AS total
FROM dentista
WHERE cpf = @cpfDentista;

-- Lista todas as consultas cadastradas
-- Metodo: listar()
SELECT *
FROM consulta;

-- Lista consultas de um paciente
-- Metodo: listarPorPaciente()
SELECT *
FROM consulta
WHERE cpfPaciente = @cpfPaciente;

-- Lista consultas de um dentista
-- Metodo: listarPorDentista()
SELECT *
FROM consulta
WHERE cpfDentista = @cpfDentista;

-- Busca uma consulta pelo ID
-- Metodo: buscarPorId()
SELECT *
FROM consulta
WHERE idConsulta = @idConsulta;

-- Verifica se existem procedimentos vinculados a uma consulta
-- Metodo: existeProcedimentos()
SELECT COUNT(*) AS total
FROM procedimento
WHERE idConsulta = @idConsulta;

-- Lista todas as consultas de um dentista especifico
-- Metodo: listarConsultasDentista()
SELECT *
FROM consulta
WHERE cpfDentista = @cpfDentista;

-- Lista todas as consultas de um paciente especifico
-- Metodo: listarConsultasPaciente()
SELECT *
FROM consulta
WHERE cpfPaciente = @cpfPaciente;

-- Verifica se uma consulta existe pelo ID, usada antes de criar procedimento
-- Metodo: existeConsulta()
SELECT COUNT(*) AS total
FROM consulta
WHERE idConsulta = @idConsulta;


-- ============================================================
-- DentistaRepository
-- ============================================================

-- Lista todos os dentistas com dados completos
-- Metodo: listar()
SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf;

-- Busca um dentista pelo CPF com dados completos
-- Metodo: buscarPorCpf()
SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf
WHERE p.cpf = @cpfDentista;

-- Verifica se o dentista possui consultas vinculadas
-- Metodo: temConsulta()
SELECT COUNT(*) AS total
FROM consulta
WHERE cpfDentista = @cpfDentista;

-- Verifica se um dentista existe pelo CPF
-- Metodo: existe()
SELECT COUNT(*) AS total
FROM dentista
WHERE cpf = @cpfDentista;

-- Busca os telefones de um dentista
-- Metodo: buscarTelefones()
SELECT telefone
FROM telefone
WHERE cpf = @cpfDentista;


-- ============================================================
-- FormularioSaudeRepository
-- ============================================================

-- Lista todos os formularios de saude
-- Metodo: listar()
SELECT *
FROM formulariosaude;

-- Busca o formulario de saude de um paciente pelo CPF
-- Metodo: buscarPorCpf()
SELECT *
FROM formulariosaude
WHERE cpfPaciente = @cpfPaciente;

-- Verifica se ja existe formulario de saude para o paciente
-- Metodo: existe()
SELECT COUNT(*) AS total
FROM formulariosaude
WHERE cpfPaciente = @cpfPaciente;


-- ============================================================
-- PacienteRepository
-- ============================================================

-- Lista todos os pacientes com dados completos
-- Metodo: listar()
SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf;

-- Busca um paciente pelo CPF com dados completos
-- Metodo: buscarPorCpf()
SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf
WHERE p.cpf = @cpfPaciente;

-- Verifica se um paciente existe pelo CPF
-- Metodo: existe()
SELECT COUNT(*) AS total
FROM paciente
WHERE cpf = @cpfPaciente;

-- Busca os telefones de um paciente
-- Metodo: buscarTelefones()
SELECT telefone
FROM telefone
WHERE cpf = @cpfPaciente;

-- Verifica se o paciente possui consultas vinculadas
-- Metodo: temConsulta()
SELECT COUNT(*) AS total
FROM consulta
WHERE cpfPaciente = @cpfPaciente;


-- ============================================================
-- PessoaRepository
-- ============================================================

-- Lista todas as pessoas cadastradas
-- Metodo: listar()
SELECT *
FROM pessoa;

-- Busca telefones de uma pessoa
-- Metodo: listar()
SELECT telefone
FROM telefone
WHERE cpf = @cpfPessoa;

-- Busca uma pessoa pelo CPF
-- Metodo: buscarPorCpf()
SELECT *
FROM pessoa
WHERE cpf = @cpfPessoa;

-- Busca telefones de uma pessoa
-- Metodo: buscarPorCpf()
SELECT telefone
FROM telefone
WHERE cpf = @cpfPessoa;

-- Verifica se uma pessoa existe pelo CPF
-- Metodo: existe()
SELECT COUNT(*) AS total
FROM pessoa
WHERE cpf = @cpfPessoa;


-- ============================================================
-- ProcedimentoRepository
-- ============================================================

-- Recupera o ID do ultimo procedimento inserido
-- Metodo: criarProcedimento()
SELECT LAST_INSERT_ID() AS idProcedimento;

-- Lista todos os procedimentos
-- Metodo: listar()
SELECT *
FROM procedimento;

-- Busca todos os procedimentos de uma consulta
-- Metodo: buscarPorIdConsulta()
SELECT *
FROM procedimento
WHERE idConsulta = @idConsulta;

-- Busca um procedimento pelo ID
-- Metodo: buscarPorIdProcedimento()
SELECT *
FROM procedimento
WHERE idProcedimento = @idProcedimento;


-- ============================================================
-- RelatorioRepository - leitura das views
-- ============================================================

-- Le todos os registros da View 1
-- Metodo: listarPacientesComAlertaSaude()
SELECT *
FROM vw_pacientes_com_alerta_saude
ORDER BY nome ASC;

-- Le todos os registros da View 2
-- Metodo: listarDentistasSemConsultaFutura()
SELECT *
FROM vw_dentistas_sem_consulta_futura
ORDER BY nome ASC;


-- ============================================================
-- CONSULTAS ANALITICAS
-- ============================================================

-- ------------------------------------------------------------
-- CONSULTA 1 - JOIN + GROUP BY + HAVING
-- Dentistas que realizaram ao menos N consultas.
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
HAVING COUNT(c.idConsulta) >= @minimoConsultas
ORDER BY totalConsultas DESC;


-- ------------------------------------------------------------
-- CONSULTA 2 - 2 JOINs + WHERE
-- Historico completo de consultas de um paciente.
-- ------------------------------------------------------------
SELECT
    c.idConsulta,
    c.cpfPaciente,
    p_pac.nome AS nomePaciente,
    c.cpfDentista,
    p_den.nome AS nomeDentista,
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
WHERE c.cpfPaciente = @cpfPaciente
ORDER BY
    c.dataConsulta DESC,
    c.horaConsulta DESC;


-- ------------------------------------------------------------
-- CONSULTA 3 - ANTI JOIN (LEFT JOIN + IS NULL)
-- Pacientes cadastrados que nunca agendaram uma consulta.
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
WHERE c.idConsulta IS NULL
ORDER BY pe.nome ASC;


-- ------------------------------------------------------------
-- CONSULTA 4 - SUBCONSULTA
-- Procedimentos das consultas do dentista com mais atendimentos.
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
    SELECT cpfDentista
    FROM consulta
    GROUP BY cpfDentista
    ORDER BY COUNT(*) DESC
    LIMIT 1
)
ORDER BY
    pr.idConsulta ASC,
    pr.idProcedimento ASC;
