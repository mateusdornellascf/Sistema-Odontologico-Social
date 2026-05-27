create index idx_pessoa_nome on
   pessoa (
      nome
   );


create index idx_consulta_dentista_data_hora on
   consulta (
      cpfdentista,
      dataconsulta,
      horaconsulta
   );