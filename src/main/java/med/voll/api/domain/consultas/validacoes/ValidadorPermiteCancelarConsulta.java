package med.voll.api.domain.consultas.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorPermiteCancelarConsulta implements ValidadorAgendamentoDeConsuta{
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora,dataConsulta).toHours();

        if(diferencaEmHoras < 24)
            throw new ValidacaoException("O cancelamento da consulta deve ser solicitado com antecedencia minima de 24 horas");
    }
}
