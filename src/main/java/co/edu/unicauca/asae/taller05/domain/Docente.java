package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @brief Subclase de Persona para docentes.
 * @details Relación 1–1 opcional con Oficina. Se habilita cascade PERSIST para
 *          que
 *          al crear un Docente se pueda guardar su Oficina con un solo save.
 *          (Ver relaciones 1–1 y tipos de cascada en JPA/Hibernate).
 */
@Entity
@Getter
@Setter
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "docId")
public class Docente extends Persona {

    @OneToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oficina_id", unique = true)
    private Oficina docOficina;

}
