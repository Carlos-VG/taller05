package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @brief Subclase de Persona para administrativos.
 * @details La guía del taller solo requiere incluirla en la jerarquía; sin
 *          lógica adicional.
 */
@Entity
@Getter
@Setter
@Table(name = "administrativo")
@PrimaryKeyJoinColumn(name = "admId")
public class Administrativo extends Persona {

    @Column(length = 255, nullable = false)
    private String rol;
}
