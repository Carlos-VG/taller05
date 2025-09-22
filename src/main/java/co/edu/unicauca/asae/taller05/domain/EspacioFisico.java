package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;

/**
 * @brief Entidad que representa un espacio físico (aula/laboratorio).
 * @details Nombre único. Capacidad y ubicación opcionales.
 */
@Entity
@Table(name = "espacio_fisico", uniqueConstraints = @UniqueConstraint(columnNames = "espNombre"))
public class EspacioFisico {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long espId;

    /** Nombre único del espacio (obligatorio). */
    @Column(nullable = false, unique = true, length = 120)
    private String espNombre;

    /** Ubicación referencial (ej. bloque/piso). */
    @Column(length = 120)
    private String espUbicacion;

    /** Capacidad aproximada (opcional). */
    @Column
    private Integer espCapacidad;

    // ===== Getters & Setters =====
    public Long getEspId() {
        return espId;
    }

    public String getEspNombre() {
        return espNombre;
    }

    public void setEspNombre(String espNombre) {
        this.espNombre = espNombre;
    }

    public String getEspUbicacion() {
        return espUbicacion;
    }

    public void setEspUbicacion(String espUbicacion) {
        this.espUbicacion = espUbicacion;
    }

    public Integer getEspCapacidad() {
        return espCapacidad;
    }

    public void setEspCapacidad(Integer espCapacidad) {
        this.espCapacidad = espCapacidad;
    }
}
