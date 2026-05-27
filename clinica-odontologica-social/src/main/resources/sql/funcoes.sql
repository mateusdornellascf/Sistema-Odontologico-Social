USE db_clinica_odontologica;
 
DELIMITER $$
CREATE FUNCTION func_calcular_idade_pessoa(
    p_cpfPessoa VARCHAR(11)
)
RETURNS INT
NOT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_dataNascimento DATE;
    DECLARE v_idade INT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_dataNascimento = NULL;
 
    SELECT data_nascimento
    INTO v_dataNascimento
    FROM pessoa
    WHERE CPF = p_cpfPessoa
    LIMIT 1;
 
    IF v_dataNascimento IS NULL THEN
        RETURN NULL;
    END IF;
 
    SET v_idade = TIMESTAMPDIFF(YEAR, v_dataNascimento, CURDATE());
 
    RETURN v_idade;
END $$
DELIMITER ;
 
 
DELIMITER $$
CREATE FUNCTION func_classificar_risco_saude(
    p_cpfPaciente VARCHAR(11)
)
RETURNS VARCHAR(20)
NOT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_alergia VARCHAR(50);
    DECLARE v_medicamento VARCHAR(50);
    DECLARE v_doencas VARCHAR(50);
    DECLARE v_totalRiscos INT DEFAULT 0;
    DECLARE v_qtdFormularios INT DEFAULT 0;
 
    SELECT COUNT(*)
    INTO v_qtdFormularios
    FROM formulariosaude
    WHERE cpfPaciente = p_cpfPaciente;
 
    IF v_qtdFormularios = 0 THEN
        RETURN 'SEM FORMULARIO';
    END IF;
 
    SELECT alergia, medicamento, doencas
    INTO v_alergia, v_medicamento, v_doencas
    FROM formulariosaude
    WHERE cpfPaciente = p_cpfPaciente
    ORDER BY idFormulario DESC
    LIMIT 1;
 
    IF v_alergia IS NOT NULL
       AND TRIM(v_alergia) <> ''
       AND LOWER(TRIM(v_alergia)) NOT IN ('nenhum', 'nenhuma', 'nao', 'n/a') THEN
        SET v_totalRiscos = v_totalRiscos + 1;
    END IF;
 
    IF v_medicamento IS NOT NULL
       AND TRIM(v_medicamento) <> ''
       AND LOWER(TRIM(v_medicamento)) NOT IN ('nenhum', 'nenhuma', 'nao', 'n/a') THEN
        SET v_totalRiscos = v_totalRiscos + 1;
    END IF;
 
    IF v_doencas IS NOT NULL
       AND TRIM(v_doencas) <> ''
       AND LOWER(TRIM(v_doencas)) NOT IN ('nenhum', 'nenhuma', 'nao', 'n/a') THEN
        SET v_totalRiscos = v_totalRiscos + 1;
    END IF;
 
    IF v_totalRiscos = 0 THEN
        RETURN 'SEM RISCO';
    ELSEIF v_totalRiscos = 1 THEN
        RETURN 'ATENCAO';
    ELSE
        RETURN 'ALTO RISCO';
    END IF;
END $$
DELIMITER ;