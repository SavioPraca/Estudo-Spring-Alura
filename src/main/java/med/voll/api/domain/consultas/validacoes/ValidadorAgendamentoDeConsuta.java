package med.voll.api.domain.consultas.validacoes;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public interface ValidadorAgendamentoDeConsuta {

    void validar(DadosAgendamentoConsulta dados);
}
