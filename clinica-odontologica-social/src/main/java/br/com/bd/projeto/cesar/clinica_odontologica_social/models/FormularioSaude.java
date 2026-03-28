package br.com.bd.projeto.cesar.clinica_odontologica_social.models;

public class FormularioSaude {
    private int idFormulario;
    private String identificacaoPaciente;
    private String alergias;
    private String doenças;
    private String medicamentos;

    public FormularioSaude() {}

    public FormularioSaude(int idFormulario, String identificacaoPaciente, String alergias, String doenças, String medicamentos) {
        this.idFormulario = idFormulario;
        this.identificacaoPaciente = identificacaoPaciente;
        this.alergias = alergias;
        this.doenças = doenças;
        this.medicamentos = medicamentos;
    }

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getIdentificacaoPaciente() {
        return identificacaoPaciente;
    }

    public void setIdentificacaoPaciente(String identificacaoPaciente) {
        this.identificacaoPaciente = identificacaoPaciente;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getDoencas() {
        return doenças;
    }

    public void setDoencas(String doenças) {
        this.doenças = doenças;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }
}
