package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Telefone {
    private String Cpf;
    private String telefone;

    public Telefone() {}

    public Telefone(String Cpf, String telefone) {
        this.Cpf = Cpf;
        this.telefone = telefone;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String Cpf) {
        this.Cpf = Cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
