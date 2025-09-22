package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;

/**
 * @brief Entidad base de la jerarquía de personas.
 * @details Estrategia JOINED para mantener normalización y evitar nulos de
 *          subclases.
 *          El correo es único. (Ver herencia JPA y restricciones de columna).
 *          Referencia: herencia en JPA SINGLE_TABLE / JOINED / TABLE_PER_CLASS.
 */
@Entity
@Table(name = "persona", uniqueConstraints = @UniqueConstraint(columnNames = "perCorreo"))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

    /** Identificador interno autoincremental. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perId;

    /** Nombres de la persona (obligatorio, máx. 60). */
    @Column(nullable = false, length = 60)
    private String perNombres;

    /** Apellidos de la persona (obligatorio, máx. 60). */
    @Column(nullable = false, length = 60)
    private String perApellidos;

    /** Correo institucional o personal único (obligatorio). */
    @Column(nullable = false, unique = true, length = 120)
    private String perCorreo;

    // ===== Getters & Setters (JavaBean) =====
    public Long getPerId() {
        return perId;
    }

    public String getPerNombres() {
        return perNombres;
    }

    public void setPerNombres(String perNombres) {
        this.perNombres = perNombres;
    }

    public String getPerApellidos() {
        return perApellidos;
    }

    public void setPerApellidos(String perApellidos) {
        this.perApellidos = perApellidos;
    }

    public String getPerCorreo() {
        return perCorreo;
    }

    public void setPerCorreo(String perCorreo) {
        this.perCorreo = perCorreo;
    }
}
