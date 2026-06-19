package br.com.bd.projeto.cesar.clinica_odontologica_social.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}