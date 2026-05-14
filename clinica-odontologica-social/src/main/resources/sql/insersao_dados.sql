USE db_clinica_odontologica;

INSERT INTO pessoa (CPF, nome, data_nascimento, cep, rua, bairro, numero) VALUES
('12345678901', 'Joao Silva', '1990-05-10', '50000-000', 'Rua das Flores', 'Boa Viagem', 101),
('98765432100', 'Maria Oliveira', '1985-08-22', '51000-000', 'Av. Recife', 'Imbiribeira', 202),
('11122233344', 'Carlos Souza', '1978-02-15', '52000-000', 'Rua do Sol', 'Casa Amarela', 303),
('55566677788', 'Ana Costa', '1995-11-30', '53000-000', 'Rua da Aurora', 'Santo Amaro', 404),
('99988877766', 'Pedro Santos', '2000-01-20', '54000-000', 'Rua do Comercio', 'Centro', 505);

INSERT INTO telefone (CPF, telefone) VALUES
('12345678901', '81999990001'),
('12345678901', '81988880001'),
('98765432100', '81999990002'),
('11122233344', '81999990003'),
('55566677788', '81999990004');

INSERT INTO paciente (CPF, numPlanoSaude) VALUES
('12345678901', 'PLANO123'),
('98765432100', 'PLANO456'),
('11122233344', 'PLANO789');

INSERT INTO dentista (CPF, cro, especialidade, email, coordenador) VALUES
('99988877766', 'CRO5678', 'Cirurgia', 'pedro.santos@email.com', NULL),
('55566677788', 'CRO1234', 'Ortodontia', 'ana.costa@email.com', '99988877766');

INSERT INTO formulariosaude (cpfPaciente, alergia, medicamento, doencas) VALUES
('12345678901', 'Nenhuma', 'Nenhum', 'Nenhuma'),
('98765432100', 'Penicilina', 'Paracetamol', 'Asma'),
('11122233344', 'Poeira', 'Ibuprofeno', 'Rinite');

INSERT INTO consulta (idConsulta, cpfPaciente, cpfDentista, dataConsulta, horaConsulta) VALUES
(1, '12345678901', '55566677788', '2026-04-01', '09:00:00'),
(2, '98765432100', '99988877766', '2026-04-02', '10:30:00'),
(3, '11122233344', '55566677788', '2026-04-03', '14:00:00');

INSERT INTO procedimento (idProcedimento, idConsulta, nomeProcedimento, descricao) VALUES
(1, 1, 'Limpeza', 'Limpeza dentaria simples'),
(2, 2, 'Extracao', 'Remocao de dente'),
(3, 3, 'Clareamento', 'Clareamento dental');

INSERT INTO cirurgico (idProcedimento, dataCirurgia, cpfCirurgiaoDentista, valor) VALUES
(2, '2026-04-02', '99988877766', 500.00);

INSERT INTO estetico (idProcedimento, quantidadeSessoes, dataSessoes, valor) VALUES
(3, 3, '2026-04-10', 800.00);

INSERT INTO rotina (idProcedimento, dataProcedimentoRotina, statusProcedimento, valor) VALUES
(1, '2026-04-01', 'REALIZADO', 150.00);
