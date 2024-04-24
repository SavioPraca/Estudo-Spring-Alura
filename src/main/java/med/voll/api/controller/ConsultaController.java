package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.AgendaDeConsultas;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static med.voll.api.infra.springdoc.SpringDocConfiguration.TOKEN_SECURITY;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = TOKEN_SECURITY)
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsulta dados){
       var dto = agenda.agendar(dados);
        System.out.println(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelar (@PathVariable Long id){

        var consultaCancelada = agenda.cancelarConsulta(id);

        if(!consultaCancelada)
            throw new ValidacaoException("DEU RUIM MAIN");

        return ResponseEntity.noContent().build();

    }
}
