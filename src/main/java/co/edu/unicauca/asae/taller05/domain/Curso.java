package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import java.util.*;

/**
 * @brief Curso de una asignatura con uno o varios docentes y varias franjas.
 * @details Importante: cascade REMOVE en 'curFranjas' para cumplir
 *          requerimiento
 *          de eliminación del curso y sus franjas en cascada.
 */
@Entity
@Table(name = "curso")
public class Curso {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long curId;

    /** Nombre del curso (ej. "Arquitecturas Empresariales - Grupo A"). */
    @Column(nullable = false, length = 120)
    private String curNombre;

    /** Asignatura a la que pertenece el curso. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asignatura_id")
    private Asignatura curAsignatura;

    /** Docentes que dictan el curso (lado propietario). */
    @ManyToMany
    @JoinTable(name = "curso_docente", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "docente_id"))
    private Set<Docente> curDocentes = new HashSet<>();

    /**
     * Franjas horarias asociadas al curso.
     * - cascade = REMOVE para eliminar franjas al borrar el curso.
     * - orphanRemoval = true asegura limpieza de huérfanos.
     */
    @OneToMany(mappedBy = "fraCurso", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FranjaHoraria> curFranjas = new ArrayList<>();

    // ===== Getters & Setters =====
    public Long getCurId() {
        return curId;
    }

    public String getCurNombre() {
        return curNombre;
    }

    public void setCurNombre(String curNombre) {
        this.curNombre = curNombre;
    }

    public Asignatura getCurAsignatura() {
        return curAsignatura;
    }

    public void setCurAsignatura(Asignatura curAsignatura) {
        this.curAsignatura = curAsignatura;
    }

    public Set<Docente> getCurDocentes() {
        return curDocentes;
    }

    public List<FranjaHoraria> getCurFranjas() {
        return curFranjas;
    }
}
