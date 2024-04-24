package med.voll.api.domain.consultas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.validacoes.ValidadorAgendamentoDeConsuta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsuta> validadores;


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente()))
            throw new ValidacaoException("Id do paciente informado não existe!");

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe!");
        }

        validadores.forEach(v->v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if(medico == null)
            throw new ValidacaoException("Não existe medico disponival na data!");

        var consulta = new Consulta(null,medico,paciente,dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());

        if(dados.especialidade() == null)
            throw new ValidacaoException("Especialidade é obrigatoria quando medico não for definido!");


        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


    public Boolean cancelarConsulta(Long id){



        var consulta = consultaRepository.getReferenceById(id);

        consultaRepository.delete(consulta);

        return consultaRepository.getReferenceById(id) != null;

    }



}
