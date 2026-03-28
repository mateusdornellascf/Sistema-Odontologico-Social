package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Paciente {

    private String cpf; 
    private String numPlanoSaude;

    public Paciente() {}
    
    public Paciente(String cpf, String numPlanoSaude) {
        this.cpf = cpf;
        this.numPlanoSaude = numPlanoSaude;
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