package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Dentista {
    private String identificacao;
    private String email;
    private String cro;
    private String especialidade;
    private String coordenador;

    public Dentista() {}

    public Dentista(String identificacao, String email, String cro, String especialidade, String coordenador) {
        this.identificacao = identificacao;
        this.email = email;
        this.cro = cro;
        this.especialidade = especialidade;
        this.coordenador = coordenador;
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
