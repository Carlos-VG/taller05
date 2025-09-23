package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @brief Entidad que representa una asignatura.
 * @details Se exige nombre único para evitar duplicados.
 */
@Entity
@Getter
@Setter
@Table(name = "asignatura", uniqueConstraints = @UniqueConstraint(columnNames = "asiNombre"))
public class Asignatura {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asiId;

    /** Nombre único de la asignatura (obligatorio). */
    @Column(nullable = false, unique = true, length = 255)
    private String asiNombre;

    @Column(nullable = false, unique = true, length = 50)
    private String asiCodigo;

}
