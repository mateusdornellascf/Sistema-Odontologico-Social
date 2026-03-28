package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Procedimento {
    private Long idProcedimento;
    private Long idConsulta;
    private Double valor;
    private String nomeProcedimento;
    private String descricao;
    
    public Long getIdProcedimento() {
        return idProcedimento;
    }
    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
    }
    public Long getIdConsulta() {
        return idConsulta;
    }
    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getNomeProcedimento() {
        return nomeProcedimento;
    }
    public void setNomeProcedimento(String nomeProcedimento) {
        this.nomeProcedimento = nomeProcedimento;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
