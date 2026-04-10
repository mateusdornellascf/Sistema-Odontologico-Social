package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Estetico extends Procedimento{
    private Long idProcedimento;
    private Integer quantidadeSessoes;
    private String dataSessoes;
    
    public Long getIdProcedimento() {
        return idProcedimento;
    }
    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
    }
    public Integer getQuantidadeSessoes() {
        return quantidadeSessoes;
    }
    public void setQuantidadeSessoes(Integer quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
    }
    public String getDataSessoes() {
        return dataSessoes;
    }
    public void setDataSessoes(String dataSessoes) {
        this.dataSessoes = dataSessoes;
    }

    
}
