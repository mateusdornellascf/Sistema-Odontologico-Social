CREATE DATABASE db_clinica_odontologica;
USE db_clinica_odontologica;

CREATE TABLE pessoa(
    CPF VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    data_nascimento DATE,
    cep VARCHAR(9),
    rua VARCHAR(50),
    bairro VARCHAR(50),
    numero INT
);

CREATE TABLE telefone(
    CPF VARCHAR(11),
    telefone VARCHAR(15),
    PRIMARY KEY (CPF, telefone),
    FOREIGN KEY(CPF) REFERENCES pessoa(CPF) ON DELETE CASCADE
);

CREATE TABLE paciente (
    CPF VARCHAR(11) PRIMARY KEY,
    numPlanoSaude VARCHAR(16),
    FOREIGN KEY (CPF) REFERENCES pessoa(CPF) ON DELETE CASCADE ON UPDATE CASCADE
);
    
CREATE TABLE dentista (
    CPF VARCHAR(11) PRIMARY KEY,
    cro VARCHAR(7) NOT NULL,
    especialidade VARCHAR(50),
    email VARCHAR(50) UNIQUE,
    coordenador VARCHAR(11),
    FOREIGN KEY (CPF) REFERENCES pessoa(CPF) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (coordenador) REFERENCES dentista(CPF) ON DELETE SET NULL
);

CREATE TABLE formulariosaude (
    idFormulario INT AUTO_INCREMENT PRIMARY KEY,
    cpfPaciente VARCHAR(11) NOT NULL,
    alergia VARCHAR(50),
    medicamento VARCHAR(50),
    doencas VARCHAR(50),
    FOREIGN KEY (cpfPaciente) REFERENCES paciente(CPF) ON DELETE CASCADE
);

CREATE TABLE consulta(
    idConsulta INT AUTO_INCREMENT PRIMARY KEY,
    cpfPaciente VARCHAR(11) NOT NULL,
    cpfDentista VARCHAR(11) NOT NULL,
    dataConsulta DATE NOT NULL,
    horaConsulta TIME,
    FOREIGN KEY(cpfPaciente) REFERENCES paciente(CPF),
    FOREIGN KEY(cpfDentista) REFERENCES dentista(CPF)
);

CREATE TABLE procedimento(
    idProcedimento INT AUTO_INCREMENT PRIMARY KEY,
    idConsulta INT NOT NULL,
    nomeProcedimento VARCHAR(50) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    FOREIGN KEY(idConsulta) REFERENCES consulta(idConsulta)
);

CREATE TABLE cirurgico(
    idProcedimento INT PRIMARY KEY,
    dataCirurgia DATE,
    cpfCirurgiaoDentista VARCHAR(11),
    valor DECIMAL CHECK (valor > 0),
    FOREIGN KEY (idProcedimento) REFERENCES procedimento(idProcedimento) ON DELETE CASCADE,
    FOREIGN KEY (cpfCirurgiaoDentista) REFERENCES dentista(CPF)
);

CREATE TABLE estetico(
    idProcedimento INT PRIMARY KEY,
    quantidadeSessoes INT NOT NULL,
    dataSessoes DATE NOT NULL,
    valor DECIMAL CHECK (valor > 0),
    FOREIGN KEY (idProcedimento) REFERENCES procedimento(idProcedimento) ON DELETE CASCADE
);

CREATE TABLE rotina(
    idProcedimento INT PRIMARY KEY,
    dataProcedimentoRotina DATE NOT NULL,
    statusProcedimento VARCHAR(20) CHECK (statusProcedimento IN ('PENDENTE', 'REALIZADO')) DEFAULT 'PENDENTE',
    valor DECIMAL CHECK (valor > 0),
    FOREIGN KEY (idProcedimento) REFERENCES procedimento(idProcedimento) ON DELETE CASCADE
);