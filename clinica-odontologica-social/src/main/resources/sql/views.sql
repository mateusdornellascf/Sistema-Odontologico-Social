-- ============================================================
-- VIEWS — Sistema Odontológico Social
-- ============================================================


-- ------------------------------------------------------------
-- VIEW 1: vw_pacientes_com_alerta_saude
--
-- Justificativa:
--   Num sistema odontológico social, antes de qualquer
--   procedimento o dentista precisa verificar se o paciente
--   possui alergias, doenças crônicas ou medicamentos contínuos
--   que possam interferir no atendimento. Esta view reúne os
--   dados de pessoa, paciente e formulariosaude em uma única
--   consulta, filtrando apenas os pacientes que possuem ao
--   menos um campo crítico de saúde preenchido. Evita que o
--   dentista precise cruzar manualmente três tabelas antes de
--   cada atendimento.
--
-- Estrutura:
--   3 JOINs: pessoa → paciente → formulariosaude
--   WHERE: filtra registros com alergia, doença ou
--          medicamento preenchidos (não nulos e não vazios)
--
-- Usada em:
--   GET /formulario-saude/alerta-saude
-- ------------------------------------------------------------

CREATE VIEW vw_pacientes_com_alerta_saude AS
SELECT
    pe.cpf,
    pe.nome,
    fs.alergia,
    fs.doencas,
    fs.medicamento
FROM pessoa pe
JOIN paciente pa
    ON pe.cpf = pa.cpf
JOIN formulariosaude fs
    ON pa.cpf = fs.cpfPaciente
WHERE
    (fs.alergia IS NOT NULL AND fs.alergia <> '')
    OR (fs.doencas IS NOT NULL AND fs.doencas <> '')
    OR (fs.medicamento IS NOT NULL AND fs.medicamento <> '');



-- ------------------------------------------------------------
-- VIEW 2: vw_dentistas_sem_consulta_futura
--
-- Justificativa:
--   A gestão da clínica precisa identificar rapidamente quais
--   dentistas estão com a agenda vazia, ou seja, sem nenhuma
--   consulta agendada a partir da data atual. Isso permite
--   redistribuir pacientes, acionar o profissional ou tomar
--   decisões administrativas com agilidade. A view usa uma
--   subconsulta correlacionada com NOT EXISTS para verificar
--   a existência de consultas futuras por dentista, evitando
--   retornar falsos positivos por consultas já realizadas.
--
-- Estrutura:
--   1 JOIN: pessoa → dentista
--   Subconsulta NOT EXISTS: verifica se há alguma consulta
--   com dataConsulta >= CURDATE() para o dentista
--
-- Usada em:
--   GET /dentista/sem-consulta-futura
-- ------------------------------------------------------------
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
