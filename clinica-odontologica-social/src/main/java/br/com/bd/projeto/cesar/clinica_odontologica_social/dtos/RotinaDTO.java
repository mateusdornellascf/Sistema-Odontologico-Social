package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class RotinaDTO {
    private String nomeProcedimento;
    private String descricao;
    private String dataProcedimentoRotina;
    private String status;
    private Double valor;

    public RotinaDTO() {
    }

    public RotinaDTO(String nomeProcedimento, String descricao, String dataProcedimentoRotina, String status, Double valor) {
        this.nomeProcedimento = nomeProcedimento;
        this.descricao = descricao;
        this.dataProcedimentoRotina = dataProcedimentoRotina;
        this.status = status;
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

    public String getDataProcedimentoRotina() {
        return dataProcedimentoRotina;
    }

    public void setDataProcedimentoRotina(String dataProcedimentoRotina) {
        this.dataProcedimentoRotina = dataProcedimentoRotina;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
