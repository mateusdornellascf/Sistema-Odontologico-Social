package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Rotina extends Procedimento{
    private Long idProcedimento;
    private String dataProcedimentoRotina;
    private String status;

    public Long getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
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
}
