package br.com.bd.projeto.cesar.clinica_odontologica_social.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {

    private int idConsulta;
    private String idPaciente; 
    private String idDentista;
    private LocalDate data;
    private LocalTime hora;

    public Consulta() {}

    public Consulta(int idConsulta, String idPaciente, String idDentista, LocalDate data, LocalTime hora) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idDentista = idDentista;
        this.data = data;
        this.hora = hora;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdDentista() {
        return idDentista;
    }

    public void setIdDentista(String idDentista) {
        this.idDentista = idDentista;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

}