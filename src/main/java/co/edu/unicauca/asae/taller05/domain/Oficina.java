package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @brief Oficina asociable a un Docente.
 * @details Nombre único para evitar duplicados; ubicación y extensión
 *          opcionales.
 *          (Ver @Column y detalles de tabla/columnas en JPA).
 */
@Entity
@Getter
@Setter
@Table(name = "oficina", uniqueConstraints = @UniqueConstraint(columnNames = "ofiNombre"))
public class Oficina {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ofiId;

    /** Nombre único de la oficina (ej. "Oficina 3-201"). */
    @Column(nullable = false, unique = true, length = 100)
    private String ofiNombre;

    /** Ubicación física (ej. bloque/piso). */
    @Column(length = 120)
    private String ofiUbicacion;

}
