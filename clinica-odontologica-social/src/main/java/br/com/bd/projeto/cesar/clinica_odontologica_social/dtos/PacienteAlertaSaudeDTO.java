package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

import java.sql.Date;

public class PacienteAlertaSaudeDTO {

    private String cpf;
    private String nome;
    private String bairro;
    private Date dataNascimento;
    private String numPlanoSaude;
    private String alergia;
    private String doencas;
    private String medicamento;

    public PacienteAlertaSaudeDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumPlanoSaude() {
        return numPlanoSaude;
    }

    public void setNumPlanoSaude(String numPlanoSaude) {
        this.numPlanoSaude = numPlanoSaude;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDoencas() {
        return doencas;
    }

    public void setDoencas(String doencas) {
        this.doencas = doencas;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }
}