package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistoricoConsultaPacienteDTO {

    private int idConsulta;
    private String cpfPaciente;
    private String nomePaciente;
    private String cpfDentista;
    private String nomeDentista;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;

    public HistoricoConsultaPacienteDTO() {
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCpfDentista() {
        return cpfDentista;
    }

    public void setCpfDentista(String cpfDentista) {
        this.cpfDentista = cpfDentista;
    }

    public String getNomeDentista() {
        return nomeDentista;
    }

    public void setNomeDentista(String nomeDentista) {
        this.nomeDentista = nomeDentista;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(LocalTime horaConsulta) {
        this.horaConsulta = horaConsulta;
    }
}
