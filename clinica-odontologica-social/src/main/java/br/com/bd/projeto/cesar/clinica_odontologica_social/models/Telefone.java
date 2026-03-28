package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Telefone {
    private String CPF;
    private String telefone;

    public Telefone() {}

    public Telefone(String CPF, String telefone) {
        this.CPF = CPF;
        this.telefone = telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
