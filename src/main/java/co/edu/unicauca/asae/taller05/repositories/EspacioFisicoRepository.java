package co.edu.unicauca.asae.taller05.repositories;

import co.edu.unicauca.asae.taller05.domain.EspacioFisico;
import org.springframework.data.jpa.repository.JpaRepository;

/** @brief Repositorio CRUD para EspacioFisico. */
public interface EspacioFisicoRepository extends JpaRepository<EspacioFisico, Long> {
}
