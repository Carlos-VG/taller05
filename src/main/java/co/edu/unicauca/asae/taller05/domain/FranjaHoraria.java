package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * @brief Franja horaria asignada a un curso, espacio físico y docente.
 * @details ManyToOne LAZY por defecto. El taller pide consultas con EAGER/LAZY
 *          controlado por método (lo haremos con @EntityGraph en el
 *          repositorio).
 */
@Entity
@Getter
@Setter
@Table(name = "franja_horaria")
public class FranjaHoraria {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fraId;

    /** Día de la semana (obligatorio). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private DiaSemana fraDia;

    /** Hora de inicio (obligatoria). */
    @Column(nullable = false)
    private LocalTime fraHoraInicio;

    /** Hora de fin (obligatoria). */
    @Column(nullable = false)
    private LocalTime fraHoraFin;

    /** Curso asociado (obligatorio). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id")
    private Curso fraCurso;

    /** Espacio físico asociado (obligatorio). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "espacio_id")
    private EspacioFisico fraEspacioFisico;

}
