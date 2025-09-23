package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.FranjaHoraria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

/**
 * @brief Repositorio para consultas específicas de franjas.
 * @details Cumple los dos requerimientos de ligado:
 *          1) Por curso: EAGER curso + espacio + docente.
 *          2) Por docente: EAGER solo curso (espacio queda LAZY hasta invocar
 *          getter).
 */
public interface FranjaHorariaRepository extends JpaRepository<FranjaHoraria, Long> {

    /** (v 0.5) Consulta de franjas por curso: EAGER curso+espacio+docente. */
    @EntityGraph(attributePaths = { "fraCurso", "fraEspacioFisico", "fraDocente" })
    List<FranjaHoraria> findByFraCurso_CurId(Long cursoId);

    /** (v 1.0) Consulta de franjas por docente: EAGER solo curso (espacio LAZY). */
    // @EntityGraph(attributePaths = { "fraCurso" })
    // List<FranjaHoraria> findByFraDocente_ocId(Long docId);
}
