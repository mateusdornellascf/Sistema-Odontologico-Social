package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class DentistaAtivoPorConsultasDTO {

    private String cpf;
    private String nome;
    private String especialidade;
    private String cro;
    private int totalConsultas;

    public DentistaAtivoPorConsultasDTO() {
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public int getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(int totalConsultas) {
        this.totalConsultas = totalConsultas;
    }
}