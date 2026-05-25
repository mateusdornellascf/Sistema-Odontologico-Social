package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

import java.time.LocalDateTime;

public class AlertaAtendimentoDTO {
    private int idAlerta;
    private int idConsulta;
    private String cpfPaciente;
    private String cpfDentista;
    private String classificacaoRisco;
    private String mensagem;
    private LocalDateTime dataGeracao;

    public int getIdAlerta() { return idAlerta; }
    public void setIdAlerta(int idAlerta) { this.idAlerta = idAlerta; }
    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }
    public String getCpfPaciente() { return cpfPaciente; }
    public void setCpfPaciente(String cpfPaciente) { this.cpfPaciente = cpfPaciente; }
    public String getCpfDentista() { return cpfDentista; }
    public void setCpfDentista(String cpfDentista) { this.cpfDentista = cpfDentista; }
    public String getClassificacaoRisco() { return classificacaoRisco; }
    public void setClassificacaoRisco(String classificacaoRisco) { this.classificacaoRisco = classificacaoRisco; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
}
