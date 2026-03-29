package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

import java.sql.Date;

public class Paciente extends Pessoa{

    private String cpf;
    private String nome;
    private String cep;
    private String bairro;
    private String numero;
    private Date dataNascimento;
 
    private String numPlanoSaude;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumPlanoSaude() {
        return numPlanoSaude;
    }

    public void setNumPlanoSaude(String numPlanoSaude) {
        this.numPlanoSaude = numPlanoSaude;
    }

}