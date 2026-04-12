package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class CirurgicoDTO {
    private Long idConsulta;
    private String nomeProcedimento;
    private String descricao;
    private String dataCirurgia;
    private String cpfCirurgiaoDentista;
    private Double valor;

    public CirurgicoDTO() {
    }

    public CirurgicoDTO(String nomeProcedimento, String descricao,
            String dataCirurgia, String cpfCirurgiaoDentista, Double valor) {
        this.nomeProcedimento = nomeProcedimento;
        this.descricao = descricao;
        this.dataCirurgia = dataCirurgia;
        this.cpfCirurgiaoDentista = cpfCirurgiaoDentista;
        this.valor = valor;
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

    public String getDataCirurgia() {
        return dataCirurgia;
    }

    public void setDataCirurgia(String dataCirurgia) {
        this.dataCirurgia = dataCirurgia;
    }

    public String getCpfCirurgiaoDentista() {
        return cpfCirurgiaoDentista;
    }

    public void setCpfCirurgiaoDentista(String cpfCirurgiaoDentista) {
        this.cpfCirurgiaoDentista = cpfCirurgiaoDentista;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}