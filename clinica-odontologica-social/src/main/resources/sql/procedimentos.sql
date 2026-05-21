use db_clinica_odontologica;

create table if not exists alerta_atendimento (
    idalerta int auto_increment primary key,
    idconsulta int not null,
    cpfpaciente varchar(11) not null,
    cpfdentista varchar(11) not null,
    classificacaorisco varchar(20) not null,
    mensagem varchar(255) not null,
    datageracao datetime not null default current_timestamp,
    foreign key (idconsulta) references consulta(idconsulta),
    foreign key (cpfpaciente) references paciente(cpf),
    foreign key (cpfdentista) references dentista(cpf)
);

delimiter $$
drop procedure if exists sp_remarcar_consulta $$
create procedure sp_remarcar_consulta(
    in p_id_consulta int,
    in p_nova_data date,
    in p_nova_hora time
)
begin
    declare v_cpf_dentista varchar(11);
    declare v_total_conflitos int default 0;
    declare v_total_consultas int default 0;

    select count(*)
    into v_total_consultas
    from consulta
    where idconsulta = p_id_consulta;

    if v_total_consultas = 0 then
        select 'consulta nao encontrada para remarcacao' as mensagem;
    else
        select cpfdentista
        into v_cpf_dentista
        from consulta
        where idconsulta = p_id_consulta;

        select count(*)
        into v_total_conflitos
        from consulta
        where cpfdentista = v_cpf_dentista
          and dataconsulta = p_nova_data
          and horaconsulta = p_nova_hora
          and idconsulta <> p_id_consulta;

        if v_total_conflitos > 0 then
            select 'dentista ja possui consulta nesse novo horario' as mensagem;
        else
            update consulta
            set dataconsulta = p_nova_data,
                horaconsulta = p_nova_hora
            where idconsulta = p_id_consulta;

            select 'consulta remarcada com sucesso' as mensagem;
        end if;
    end if;
end $$

drop procedure if exists sp_gerar_alertas_consultas_do_dia $$
create procedure sp_gerar_alertas_consultas_do_dia(
    in p_data_consulta date
)
begin
    declare v_fim_cursor int default 0;
    declare v_id_consulta int;
    declare v_cpf_paciente varchar(11);
    declare v_cpf_dentista varchar(11);
    declare v_nome_paciente varchar(50);
    declare v_nome_dentista varchar(50);
    declare v_classificacao_risco varchar(20);
    declare v_mensagem varchar(255);
    declare v_total_alertas int default 0;

    declare cur_consultas cursor for
        select
            c.idconsulta,
            c.cpfpaciente,
            c.cpfdentista,
            p_paciente.nome as nomepaciente,
            p_dentista.nome as nomedentista
        from consulta c
        join pessoa p_paciente
            on c.cpfpaciente = p_paciente.cpf
        join pessoa p_dentista
            on c.cpfdentista = p_dentista.cpf
        where c.dataconsulta = p_data_consulta
        order by c.horaconsulta, c.idconsulta;

    declare continue handler for not found set v_fim_cursor = 1;

    open cur_consultas;

    loop_consultas: loop
        fetch cur_consultas
        into v_id_consulta, v_cpf_paciente, v_cpf_dentista,
             v_nome_paciente, v_nome_dentista;

        if v_fim_cursor = 1 then
            leave loop_consultas;
        end if;

        set v_classificacao_risco = lower(fn_classificar_risco_saude(v_cpf_paciente));

        if v_classificacao_risco in ('atencao', 'alto risco') then
            select count(*)
            into v_total_alertas
            from alerta_atendimento
            where idconsulta = v_id_consulta
              and classificacaorisco = v_classificacao_risco;

            set v_mensagem = concat(
                'paciente ', v_nome_paciente,
                ' possui classificacao ', v_classificacao_risco,
                ' para atendimento com dentista ', v_nome_dentista,
                '. verificar formulario de saude antes do procedimento.'
            );

            if v_total_alertas = 0 then
                insert into alerta_atendimento (
                    idconsulta,
                    cpfpaciente,
                    cpfdentista,
                    classificacaorisco,
                    mensagem
                ) values (
                    v_id_consulta,
                    v_cpf_paciente,
                    v_cpf_dentista,
                    v_classificacao_risco,
                    v_mensagem
                );
            end if;
        end if;
    end loop;
    close cur_consultas;
end $$
delimiter ;
