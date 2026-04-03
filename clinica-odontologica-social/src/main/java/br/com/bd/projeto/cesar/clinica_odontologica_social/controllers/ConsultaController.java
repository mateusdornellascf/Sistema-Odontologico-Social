package br.com.bd.projeto.cesar.clinica_odontologica_social.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd.projeto.cesar.clinica_odontologica_social.services.ConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    public String criarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora){
        service.criarConsulta(cpfPaciente, cpfDentista, data, hora);
        return "Consulta criada com sucesso!";
    }
    public String listarConsultas(String cpfPaciente){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarConsultas'");
    }
    
    public String remarcarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remarcarConsulta'");
    }
    public String cancelarConsulta(String cpfPaciente, String cpfDentista, LocalDate data, LocalTime hora){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarConsulta'");
    }
    
}
