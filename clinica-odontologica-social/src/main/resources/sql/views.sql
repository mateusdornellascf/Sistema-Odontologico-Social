CREATE VIEW vw_pacientes_com_alerta_saude AS
SELECT
    pe.cpf,
    pe.nome,
    c.idConsulta,
    c.dataConsulta,
    c.horaConsulta,
    fs.alergia,
    fs.doencas,
    fs.medicamento
FROM pessoa pe
JOIN paciente pa
    ON pe.cpf = pa.cpf
JOIN formulariosaude fs
    ON pa.cpf = fs.cpfPaciente
JOIN consulta c
    ON pa.cpf = c.cpfPaciente
WHERE
    (fs.alergia IS NOT NULL AND fs.alergia <> '')
    OR (fs.doencas IS NOT NULL AND fs.doencas <> '')
    OR (fs.medicamento IS NOT NULL AND fs.medicamento <> '');


CREATE VIEW vw_dentistas_sem_consulta_futura AS
SELECT
    pe.cpf,
    pe.nome,
    pe.data_nascimento,
    d.especialidade,
    d.cro,
    d.email,
    d.coordenador
FROM pessoa pe
JOIN dentista d
    ON pe.cpf = d.cpf
WHERE NOT EXISTS (
    SELECT 1
    FROM consulta c
    WHERE c.cpfDentista  = d.cpf
      AND c.dataConsulta >= CURDATE()
);
