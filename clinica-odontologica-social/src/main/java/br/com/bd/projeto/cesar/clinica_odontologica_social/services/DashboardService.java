package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.DashboardRepository;

@Service
public class DashboardService {

    private final DashboardRepository repo;

    public DashboardService(DashboardRepository repo) {
        this.repo = repo;
    }

    public Map<String, Object> resumo() {
        Map<String, Object> r = new HashMap<>();

        int totalPessoas      = repo.count("pessoa");
        int totalPacientes    = repo.count("paciente");
        int totalDentistas    = repo.count("dentista");
        int totalConsultas    = repo.count("consulta");
        int totalProcedimentos = repo.count("procedimento");
        int totalFormularios  = repo.count("formulariosaude");
        int comFormulario     = repo.pacientesComFormulario();

        double pctComFormulario = totalPacientes == 0
                ? 0.0
                : Math.round((100.0 * comFormulario / totalPacientes) * 10.0) / 10.0;

        r.put("totalPessoas", totalPessoas);
        r.put("totalPacientes", totalPacientes);
        r.put("totalDentistas", totalDentistas);
        r.put("totalConsultas", totalConsultas);
        r.put("totalProcedimentos", totalProcedimentos);
        r.put("totalFormularios", totalFormularios);
        r.put("pacientesComFormulario", comFormulario);
        r.put("pctPacientesComFormulario", pctComFormulario);

        r.put("ticketMedioCirurgico", repo.ticketMedioCirurgico());
        r.put("ticketMedioEstetico", repo.ticketMedioEstetico());
        r.put("ticketMedioRotina", repo.ticketMedioRotina());
        r.put("valorTotalGeral", repo.valorTotalGeral());

        return r;
    }

    public List<Map<String, Object>> consultasPorMes(Integer ano) {
        return repo.consultasPorMes(ano);
    }

    public List<Map<String, Object>> consultasPorDentista(int limit) {
        return repo.consultasPorDentista(limit);
    }

    public List<Map<String, Object>> distribuicaoRisco() {
        return repo.distribuicaoRisco();
    }

    public List<Map<String, Object>> procedimentosPorTipo() {
        return repo.procedimentosPorTipo();
    }

    public List<Map<String, Object>> consultasPorEspecialidade() {
        return repo.consultasPorEspecialidade();
    }

    public Map<String, Object> estatisticasIdade() {
        return repo.estatisticasIdade();
    }

    public List<Map<String, Object>> pacientesPorBairro() {
        return repo.pacientesPorBairro();
    }

    public List<Map<String, Object>> consultasVsProcedimentosPorDentista() {
        return repo.consultasVsProcedimentosPorDentista();
    }
}
