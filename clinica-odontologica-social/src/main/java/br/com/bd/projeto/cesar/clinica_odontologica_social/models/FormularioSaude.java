package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class FormularioSaude {
    private Integer idFormulario;
    private String cpfPaciente;
    private String alergia;
    private String doencas;
    private String medicamento;

    public FormularioSaude() {
    }

    public FormularioSaude(Integer idFormulario, String cpfPaciente, String alergia, String doencas,
            String medicamento) {
        this.idFormulario = idFormulario;
        this.cpfPaciente = cpfPaciente;
        this.alergia = alergia;
        this.doencas = doencas;
        this.medicamento = medicamento;
    }

    public Integer getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getAlergias() {
        return alergia;
    }

    public void setAlergias(String alergia) {
        this.alergia = alergia;
    }

    public String getDoencas() {
        return doencas;
    }

    public void setDoencas(String doencas) {
        this.doencas = doencas;
    }

    public String getMedicamentos() {
        return medicamento;
    }

    public void setMedicamentos(String medicamento) {
        this.medicamento = medicamento;
    }
}
