USE db_clinica_odontologica;

SET @cpfPaciente = '12345678901';
SET @cpfDentista = '55566677788';
SET @cpfPessoa = '12345678901';
SET @idConsulta = 1;
SET @idProcedimento = 1;
SET @dataConsulta = '2026-04-01';
SET @horaConsulta = '09:00:00';
SET @minimoConsultas = 1;

SELECT COUNT(*) AS total
FROM consulta
WHERE cpfDentista = @cpfDentista
  AND dataConsulta = @dataConsulta
  AND horaConsulta = @horaConsulta;

SELECT COUNT(*) AS total
FROM consulta
WHERE idConsulta = @idConsulta;

SELECT COUNT(*) AS total
FROM paciente
WHERE cpf = @cpfPaciente;

SELECT COUNT(*) AS total
FROM dentista
WHERE cpf = @cpfDentista;

SELECT *
FROM consulta;

SELECT *
FROM consulta
WHERE cpfPaciente = @cpfPaciente;

SELECT *
FROM consulta
WHERE cpfDentista = @cpfDentista;

SELECT *
FROM consulta
WHERE idConsulta = @idConsulta;

SELECT COUNT(*) AS total
FROM procedimento
WHERE idConsulta = @idConsulta;

SELECT *
FROM consulta
WHERE cpfDentista = @cpfDentista;

SELECT *
FROM consulta
WHERE cpfPaciente = @cpfPaciente;

SELECT COUNT(*) AS total
FROM consulta
WHERE idConsulta = @idConsulta;

SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf;

SELECT p.*, d.cro, d.especialidade, d.email, d.coordenador
FROM pessoa p
JOIN dentista d ON p.cpf = d.cpf
WHERE p.cpf = @cpfDentista;

SELECT COUNT(*) AS total
FROM consulta
WHERE cpfDentista = @cpfDentista;

SELECT COUNT(*) AS total
FROM dentista
WHERE cpf = @cpfDentista;

SELECT telefone
FROM telefone
WHERE cpf = @cpfDentista;

SELECT *
FROM formulariosaude;

SELECT *
FROM formulariosaude
WHERE cpfPaciente = @cpfPaciente;

SELECT COUNT(*) AS total
FROM formulariosaude
WHERE cpfPaciente = @cpfPaciente;


SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf;

SELECT p.*, pa.numPlanoSaude
FROM pessoa p
JOIN paciente pa ON p.cpf = pa.cpf
WHERE p.cpf = @cpfPaciente;

SELECT COUNT(*) AS total
FROM paciente
WHERE cpf = @cpfPaciente;

SELECT telefone
FROM telefone
WHERE cpf = @cpfPaciente;

SELECT COUNT(*) AS total
FROM consulta
WHERE cpfPaciente = @cpfPaciente;


SELECT *
FROM pessoa;

SELECT telefone
FROM telefone
WHERE cpf = @cpfPessoa;

SELECT *
FROM pessoa
WHERE cpf = @cpfPessoa;

SELECT telefone
FROM telefone
WHERE cpf = @cpfPessoa;

SELECT COUNT(*) AS total
FROM pessoa
WHERE cpf = @cpfPessoa;

SELECT LAST_INSERT_ID() AS idProcedimento;

SELECT *
FROM procedimento;

SELECT *
FROM procedimento
WHERE idConsulta = @idConsulta;

SELECT *
FROM procedimento
WHERE idProcedimento = @idProcedimento;


SELECT *
FROM vw_pacientes_com_alerta_saude
ORDER BY nome ASC;


SELECT *
FROM vw_dentistas_sem_consulta_futura
ORDER BY nome ASC;


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


SELECT
    c.idConsulta,
    c.cpfPaciente,
    p_pac.nome AS nomePaciente,
    c.cpfDentista,
    p_den.nome AS nomeDentista,
    c.dataConsulta,
    c.horaConsulta
FROM consulta c
JOIN pessoa p_pac
    ON c.cpfPaciente = p_pac.cpf
JOIN pessoa p_den
    ON c.cpfDentista = p_den.cpf
WHERE c.cpfPaciente = @cpfPaciente
ORDER BY
    c.dataConsulta DESC,
    c.horaConsulta DESC;


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
