package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

/** @brief Repositorio CRUD para Docente. */
public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
