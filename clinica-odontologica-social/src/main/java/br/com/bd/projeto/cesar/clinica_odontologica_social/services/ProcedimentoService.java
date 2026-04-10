package br.com.bd.projeto.cesar.clinica_odontologica_social.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.CirurgicoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.EsteticoDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.dtos.RotinaDTO;
import br.com.bd.projeto.cesar.clinica_odontologica_social.models.Procedimento;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.ConsultaRepository;
import br.com.bd.projeto.cesar.clinica_odontologica_social.repository.ProcedimentoRepository;

@Service
public class ProcedimentoService {
    private final ConsultaRepository consultaRepository;

    private final ProcedimentoRepository procedimentoRepository;

    public ProcedimentoService(ProcedimentoRepository procedimentoRepository, ConsultaRepository consultaRepository) {
        this.procedimentoRepository = procedimentoRepository;
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public Long criarCirurgico(Long idConsulta, CirurgicoDTO dto) {
        if (!consultaRepository.existeConsulta(idConsulta)) {
            throw new RuntimeException("Consulta não encontrada");
        }

        Procedimento p = new Procedimento();
        p.setIdConsulta(idConsulta);
        p.setNomeProcedimento(dto.getNomeProcedimento());
        p.setDescricao(dto.getDescricao());

        Long idProcedimento = procedimentoRepository.criarProcedimento(p);

        procedimentoRepository.criarCirurgico(
                idProcedimento,
                dto.getDataCirurgia(),
                dto.getCpfCirurgiaoDentista(),
                dto.getValor());

        return idProcedimento;
    }

    @Transactional
    public Long criarEstetico(Long idConsulta, EsteticoDTO dto) {
        if (!consultaRepository.existeConsulta(idConsulta)) {
            throw new RuntimeException("Consulta não encontrada");
        }

        Procedimento p = new Procedimento();
        p.setIdConsulta(idConsulta);
        p.setNomeProcedimento(dto.getNomeProcedimento());
        p.setDescricao(dto.getDescricao());

        Long idProcedimento = procedimentoRepository.criarProcedimento(p);

        procedimentoRepository.criarEstetico(
                idProcedimento,
                dto.getQuantidadeSessoes(),
                dto.getDataSessoes(),
                dto.getValor());
        return idProcedimento;
    }

    @Transactional
    public Long criarRotina(Long idConsulta, RotinaDTO dto) {
        if (!consultaRepository.existeConsulta(idConsulta)) {
            throw new RuntimeException("Consulta não encontrada");
        }

        Procedimento p = new Procedimento();
        p.setIdConsulta(idConsulta);
        p.setNomeProcedimento(dto.getNomeProcedimento());
        p.setDescricao(dto.getDescricao());

        Long idProcedimento = procedimentoRepository.criarProcedimento(p);

        procedimentoRepository.criarRotina(
                idProcedimento,
                dto.getDataProcedimentoRotina(),
                dto.getStatus(),
                dto.getValor());
        return idProcedimento;
    }

    public List<Procedimento> buscarPorConsulta(Long idConsulta) {
        return procedimentoRepository.buscarPorConsulta(idConsulta);
    }

    public Procedimento buscarPorId(Long idProcedimento, Long idConsulta) {
        return procedimentoRepository.buscarPorId(idProcedimento, idConsulta);
    }

    public boolean atualizar(Procedimento procedimento) {
        return procedimentoRepository.atualizar(procedimento);
    }

    public boolean deletar(Long idConsulta, Long idProcedimento) {
        return procedimentoRepository.deletar(idConsulta, idProcedimento);
    }
}