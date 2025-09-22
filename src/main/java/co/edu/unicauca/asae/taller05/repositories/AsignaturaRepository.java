package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

/** @brief Repositorio CRUD para Asignatura. */
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
}
