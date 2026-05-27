use db_clinica_odontologica;

create table if not exists log_consulta (
    idlog int auto_increment primary key,
    idconsulta int not null,
    cpfpaciente varchar(11) not null,
    cpfdentista varchar(11) not null,
    dataantiga date,
    horaantiga time,
    datanova date,
    horanova time,
    operacao varchar(30) not null,
    datalog datetime not null default current_timestamp
);

delimiter $$

create trigger t_log_remarcacao_consulta
after update on consulta
for each row
begin
    if not (old.dataconsulta <=> new.dataconsulta)
       or not (old.horaconsulta <=> new.horaconsulta) then
        insert into log_consulta (
            idconsulta,
            cpfpaciente,
            cpfdentista,
            dataantiga,
            horaantiga,
            datanova,
            horanova,
            operacao
        ) values (
            old.idconsulta,
            old.cpfpaciente,
            old.cpfdentista,
            old.dataconsulta,
            old.horaconsulta,
            new.dataconsulta,
            new.horaconsulta,
            'remarcacao'
        );
    end if;
end $$

create trigger t_impedir_conflito_agenda_dentista
before insert on consulta
for each row
begin
    declare v_total_conflitos int default 0;

    select count(*)
    into v_total_conflitos
    from consulta
    where cpfdentista = new.cpfdentista
      and dataconsulta = new.dataconsulta
      and horaconsulta = new.horaconsulta;

    if v_total_conflitos > 0 then
        signal sqlstate '45000'
            set message_text = 'dentista ja possui consulta cadastrada nesse horario';
    end if;
end $$

delimiter ;
