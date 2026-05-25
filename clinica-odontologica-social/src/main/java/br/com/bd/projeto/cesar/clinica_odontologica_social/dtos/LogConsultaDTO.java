package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LogConsultaDTO {
    private int idLog;
    private int idConsulta;
    private String cpfPaciente;
    private String cpfDentista;
    private LocalDate dataAntiga;
    private LocalTime horaAntiga;
    private LocalDate dataNova;
    private LocalTime horaNova;
    private String operacao;
    private LocalDateTime dataLog;

    public int getIdLog() { return idLog; }
    public void setIdLog(int idLog) { this.idLog = idLog; }
    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }
    public String getCpfPaciente() { return cpfPaciente; }
    public void setCpfPaciente(String cpfPaciente) { this.cpfPaciente = cpfPaciente; }
    public String getCpfDentista() { return cpfDentista; }
    public void setCpfDentista(String cpfDentista) { this.cpfDentista = cpfDentista; }
    public LocalDate getDataAntiga() { return dataAntiga; }
    public void setDataAntiga(LocalDate dataAntiga) { this.dataAntiga = dataAntiga; }
    public LocalTime getHoraAntiga() { return horaAntiga; }
    public void setHoraAntiga(LocalTime horaAntiga) { this.horaAntiga = horaAntiga; }
    public LocalDate getDataNova() { return dataNova; }
    public void setDataNova(LocalDate dataNova) { this.dataNova = dataNova; }
    public LocalTime getHoraNova() { return horaNova; }
    public void setHoraNova(LocalTime horaNova) { this.horaNova = horaNova; }
    public String getOperacao() { return operacao; }
    public void setOperacao(String operacao) { this.operacao = operacao; }
    public LocalDateTime getDataLog() { return dataLog; }
    public void setDataLog(LocalDateTime dataLog) { this.dataLog = dataLog; }
}
