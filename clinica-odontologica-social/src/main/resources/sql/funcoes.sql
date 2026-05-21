USE db_clinica_odontologica;

DELIMITER $$
create function func_calcular_idade_pessoa(
    p_cpfPessoa varchar(11)
)
returns int
not deterministic
READS SQL DATA
begin
    declare v_dataNascimento date;
    declare v_idade int;
    declare continue handler for not found set v_dataNascimento = NULL;

    select data_nascimento
    into v_dataNascimento
    from pessoa
    where CPF = p_cpfPessoa
    limit 1;

    if v_dataNascimento is null then
        return null;
    end if;

    set v_idade = TIMESTAMPDIFF(YEAR, v_dataNascimento, CURDATE());

    return v_idade;
end $$


DELIMITER $$
create function func_classificar_risco_saude(
    p_cpfPaciente varchar(11)
)
returns varchar(20)
deterministic
reads sql data
begin
    declare v_alergia varchar(50);
    declare v_medicamento varchar(50);
    declare v_doencas varchar(50);
    declare v_totalRiscos int default 0;
    declare v_qtdFormularios int default 0;

    select COUNT(*)
    into v_qtdFormularios
    from formulariosaude
    where cpfPaciente = p_cpfPaciente;

    if v_qtdFormularios = 0 then
        return 'SEM FORMULARIO';
    end if;

    select alergia, medicamento, doencas
    into v_alergia, v_medicamento, v_doencas
    from formulariosaude
    where cpfPaciente = p_cpfPaciente
    order by idFormulario desc
    limit 1;

    if v_alergia is not null
       and TRIM(v_alergia) <> ''
       and LOWER(TRIM(v_alergia)) not in ('nenhum', 'nenhuma', 'nao', 'n/a') then
        set v_totalRiscos = v_totalRiscos + 1;
    end if;

    if v_medicamento IS NOT NULL
       and TRIM(v_medicamento) <> ''
       and LOWER(TRIM(v_medicamento)) not in ('nenhum', 'nenhuma', 'nao', 'n/a') then
        set v_totalRiscos = v_totalRiscos + 1;
    end if;

    IF v_doencas IS NOT NULL
       and TRIM(v_doencas) <> ''
       and LOWER(TRIM(v_doencas)) not in('nenhum', 'nenhuma', 'nao', 'n/a') then
        set v_totalRiscos = v_totalRiscos + 1;
    end if;

    if v_totalRiscos = 0 then
        return 'SEM RISCO';
    elseif v_totalRiscos = 1 then
        return 'ATENCAO';
    else
        return 'ALTO RISCO';
    end if;
end $$
DELIMITER ;

