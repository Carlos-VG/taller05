package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;

/**
 * @brief Entidad que representa una asignatura.
 * @details Se exige nombre único para evitar duplicados.
 */
@Entity
@Table(name = "asignatura", uniqueConstraints = @UniqueConstraint(columnNames = "asiNombre"))
public class Asignatura {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asiId;

    /** Nombre único de la asignatura (obligatorio). */
    @Column(nullable = false, unique = true, length = 120)
    private String asiNombre;

    // ===== Getters & Setters =====
    public Long getAsiId() {
        return asiId;
    }

    public String getAsiNombre() {
        return asiNombre;
    }

    public void setAsiNombre(String asiNombre) {
        this.asiNombre = asiNombre;
    }
}
