package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class Cirurgico extends Procedimento {
    private Long idProcedimento;
    private String nomeCirurgiaoDentista;
    private String dataCirurgia;
    public Long getIdProcedimento() {
        return idProcedimento;
    }
    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
    }
    public String getNomeCirurgiaoDentista() {
        return nomeCirurgiaoDentista;
    }
    public void setNomeCirurgiaoDentista(String nomeCirurgiaoDentista) {
        this.nomeCirurgiaoDentista = nomeCirurgiaoDentista;
    }
    public String getDataCirurgia() {
        return dataCirurgia;
    }
    public void setDataCirurgia(String dataCirurgia) {
        this.dataCirurgia = dataCirurgia;
    }

    
}
