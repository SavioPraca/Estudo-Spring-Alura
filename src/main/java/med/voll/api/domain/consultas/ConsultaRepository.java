package med.voll.api.domain.consultas;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

//    @Query("""
//            select c from Consulta c
//            where c.data =: data
//            and c.medico.id =: idMedico
//               """)
    Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

//    @Query("""
//            select c from Consulta c
//            where c.paciente.id =: idPaciente
//           and
//            """)
    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorarioDoDia, LocalDateTime ultimoHorarioDoDia);
}