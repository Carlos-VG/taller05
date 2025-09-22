package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

/** @brief Repositorio CRUD para Curso. */
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
