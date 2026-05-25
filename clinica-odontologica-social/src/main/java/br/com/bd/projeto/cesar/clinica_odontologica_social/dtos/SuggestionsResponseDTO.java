package br.com.bd.projeto.cesar.clinica_odontologica_social.dtos;

import java.util.List;

public class SuggestionsResponseDTO {

    private List<String> suggestions;

    public SuggestionsResponseDTO(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}