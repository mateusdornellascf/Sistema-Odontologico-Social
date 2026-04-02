package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

import java.sql.Date;
public class Dentista extends Pessoa{
    private String cpf;
    private String nome;
    private String cep;
    private String bairro;
    private String numero;
    private Date dataNascimento;

    private String identificacao;
    private String email;
    private String cro;
    private String especialidade;
    private String coordenador;

    public Dentista() {}

    

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

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }
}
