CREATE INDEX idx_consulta_cpfPaciente ON
consulta (cpfPaciente);

CREATE INDEX idx_paciente_nome ON
pessoa (nome);

create index idx_consulta_cpfDentista on
   consulta (
      cpfdentista,
      dataconsulta,
      horaconsulta
   );