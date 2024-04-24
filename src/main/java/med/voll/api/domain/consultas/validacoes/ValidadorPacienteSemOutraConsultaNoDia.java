package med.voll.api.domain.consultas.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsuta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorarioDoDia = dados.data().withHour(7);
        var ultimoHorarioDoDia = dados.data().withHour(18);

        var pacientePossuiOutraConsultaNoMesmoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorarioDoDia,ultimoHorarioDoDia);
        if(pacientePossuiOutraConsultaNoMesmoDia)
            throw new ValidacaoException("Paciente ja possui consulta agendada nesse Dia");
    }
}
