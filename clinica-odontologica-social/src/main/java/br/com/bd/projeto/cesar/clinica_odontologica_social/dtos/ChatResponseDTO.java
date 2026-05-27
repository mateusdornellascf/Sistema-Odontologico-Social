package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

public class ChatResponseDTO {

    private String resposta;

    public ChatResponseDTO(String resposta) {
        this.resposta = resposta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}