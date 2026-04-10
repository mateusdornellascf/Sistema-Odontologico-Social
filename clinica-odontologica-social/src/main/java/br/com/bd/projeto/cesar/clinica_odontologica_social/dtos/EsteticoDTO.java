package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class EsteticoDTO {
    private String nomeProcedimento;
    private String descricao;
    private int quantidadeSessoes;
    private String dataSessoes;
    private Double valor;

    public EsteticoDTO() {
    }

    public EsteticoDTO(String nomeProcedimento, String descricao,
            String dataSessoes, int quantidadeSessoes, Double valor) {
        this.nomeProcedimento = nomeProcedimento;
        this.descricao = descricao;
        this.dataSessoes = dataSessoes;
        this.quantidadeSessoes = quantidadeSessoes;
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

    public int getQuantidadeSessoes() {
        return quantidadeSessoes;
    }

    public void setQuantidadeSessoes(int quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDataSessoes() {
        return dataSessoes;
    }

    public void setDataSessoes(String dataSessoes) {
        this.dataSessoes = dataSessoes;
    }
    

}
