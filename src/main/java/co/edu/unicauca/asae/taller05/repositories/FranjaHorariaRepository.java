package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.FranjaHoraria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // NUEVO: por docente -> join a través del curso.curDocentes
    @EntityGraph(attributePaths = { "fraCurso", "fraCurso.curDocentes", "fraEspacioFisico" })
    @Query("""
            SELECT f
              FROM FranjaHoraria f
              JOIN f.fraCurso c
              JOIN c.curDocentes d
             WHERE d.perId = :docId
            """)
    List<FranjaHoraria> findByDocenteId(@Param("docId") Long docenteId);

}
