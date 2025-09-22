package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;

/**
 * @brief Subclase de Persona para docentes.
 * @details Relación 1–1 opcional con Oficina. Se habilita cascade PERSIST para
 *          que
 *          al crear un Docente se pueda guardar su Oficina con un solo save.
 *          (Ver relaciones 1–1 y tipos de cascada en JPA/Hibernate).
 */
@Entity
@Table(name = "docente")
public class Docente extends Persona {

    /**
     * Oficina asignada (opcional). Se usa columna foránea en docentes.oficina_id.
     */
    @OneToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oficina_id", unique = true)
    private Oficina docOficina;

    // ===== Getters & Setters =====
    public Oficina getDocOficina() {
        return docOficina;
    }

    public void setDocOficina(Oficina docOficina) {
        this.docOficina = docOficina;
    }
}
