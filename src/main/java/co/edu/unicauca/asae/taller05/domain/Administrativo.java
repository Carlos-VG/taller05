package co.edu.unicauca.asae.taller05.domain;

import jakarta.persistence.*;

/**
 * @brief Subclase de Persona para administrativos.
 * @details La guía del taller solo requiere incluirla en la jerarquía; sin
 *          lógica adicional.
 */
@Entity
@Table(name = "administrativo")
public class Administrativo extends Persona {
    // Intencionalmente vacío: el taller no exige más campos aquí.
}
