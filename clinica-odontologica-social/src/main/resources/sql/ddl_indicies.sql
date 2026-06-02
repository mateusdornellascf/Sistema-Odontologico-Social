CREATE INDEX idx_consulta_cpfPaciente ON
consulta (cpfPaciente);


create index idx_consulta_cpfDentista on
   consulta (
      cpfdentista,
      dataconsulta,
      horaconsulta
   );