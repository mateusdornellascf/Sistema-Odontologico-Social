package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class ProcedimentoDentistaAtivoDTO {

    private Long idProcedimento;
    private Long idConsulta;
    private String nomeProcedimento;
    private String descricao;
    private String cpfDentista;
    private String nomeDentista;

    public ProcedimentoDentistaAtivoDTO() {
    }

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

    public String getCpfDentista() {
        return cpfDentista;
    }

    public void setCpfDentista(String cpfDentista) {
        this.cpfDentista = cpfDentista;
    }

    public String getNomeDentista() {
        return nomeDentista;
    }

    public void setNomeDentista(String nomeDentista) {
        this.nomeDentista = nomeDentista;
    }
}