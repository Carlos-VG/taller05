package co.edu.unicauca.asae.taller05;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller05.domain.*;
import co.edu.unicauca.asae.taller05.repositories.*;

/**
 * @brief Punto de entrada del taller (estilo CommandLineRunner).
 * @details Implementa EXACTAMENTE lo solicitado:
 *          - v1.0: crear Docente (+Oficina) con un solo save (cascade PERSIST).
 *          - v1.0: crear Curso con getReferenceById (Asignatura/Docente).
 *          - v1.0: crear Franja con getReferenceById (Curso/Espacio/Docente).
 *          - v0.5: consultar franjas por Curso (EAGER curso+espacio+docente
 *          vía @EntityGraph).
 *          - v1.0: consultar franjas por Docente (EAGER solo curso; espacio
 *          LAZY hasta getter).
 *          - v0.5: eliminar Curso (cascade REMOVE -> borra franjas).
 *
 *          Buenas prácticas:
 *          - Prefijos por entidad (per*, ofi*, asi*, esp*, cur*, fra*).
 *          - @Transactional a nivel de métodos.
 *          - Salida clara en consola para ver el efecto de cada operación.
 */
@SpringBootApplication
public class Taller05Application implements CommandLineRunner {

	// ======= Repositorios inyectados =======
	@Autowired
	private DocenteRepository docenteRepo;
	@Autowired
	private AsignaturaRepository asignaturaRepo;
	@Autowired
	private CursoRepository cursoRepo;
	@Autowired
	private EspacioFisicoRepository espacioRepo;
	@Autowired
	private FranjaHorariaRepository franjaRepo;

	public static void main(String[] args) {
		SpringApplication.run(Taller05Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Orden sugerido para ver todo en consola:
		Long docenteId = crearDocenteConOficina(); // v1.0 (cascade PERSIST)
		Long asignaturaId = asegurarAsignaturaBase(); // usa import.sql o crea una si no existe
		Long espacioId = asegurarEspacioBase(); // usa import.sql o crea uno si no existe
		Long cursoId = crearCurso(asignaturaId, docenteId); // v1.0 (getReferenceById)
		crearFranja(cursoId, espacioId, docenteId); // v1.0 (getReferenceById)
		consultarFranjasPorCurso(cursoId); // v0.5
		consultarFranjasPorDocente(docenteId); // v1.0
		eliminarCursoEnCascada(cursoId); // v0.5
	}

	// =====================================================================
	// v1.0 — Crear Docente con Oficina en UN solo save (CascadeType.PERSIST)
	// =====================================================================
	@Transactional
	public Long crearDocenteConOficina() {
		Docente doc = new Docente();
		doc.setPerNombres("María");
		doc.setPerApellidos("Rojas");
		doc.setPerCorreo("maria.rojas@unicauca.edu.co"); // único

		Oficina ofi = new Oficina();
		ofi.setOfiNombre("Oficina 3-201"); // único
		ofi.setOfiUbicacion("Bloque 3 - Piso 2");
		ofi.setOfiExtension("3201");

		// Relación 1–1 opcional con cascade PERSIST (configurada en la entidad)
		doc.setDocOficina(ofi);

		Docente saved = docenteRepo.save(doc); // << un solo save
		System.out.println("[OK] Docente creado -> id=" + saved.getPerId()
				+ " | oficina=" + (saved.getDocOficina() != null ? saved.getDocOficina().getOfiNombre() : "N/A"));
		return saved.getPerId();
	}

	// =====================================================================================
	// Helper: si no cargaste import.sql, crea al menos una Asignatura para
	// getReferenceById
	// =====================================================================================
	@Transactional
	public Long asegurarAsignaturaBase() {
		List<Asignatura> todas = asignaturaRepo.findAll();
		if (!todas.isEmpty())
			return todas.get(0).getAsiId();
		Asignatura a = new Asignatura();
		a.setAsiNombre("Arquitectura de Software");
		Asignatura saved = asignaturaRepo.save(a);
		System.out.println("[OK] Asignatura creada -> id=" + saved.getAsiId());
		return saved.getAsiId();
	}

	// =====================================================================================
	// Helper: si no cargaste import.sql, crea al menos un Espacio Físico para las
	// franjas
	// =====================================================================================
	@Transactional
	public Long asegurarEspacioBase() {
		List<EspacioFisico> todos = espacioRepo.findAll();
		if (!todos.isEmpty())
			return todos.get(0).getEspId();
		EspacioFisico e = new EspacioFisico();
		e.setEspNombre("Aula Tulcán 101");
		e.setEspUbicacion("Bloque Tulcán - Piso 1");
		e.setEspCapacidad(40);
		EspacioFisico saved = espacioRepo.save(e);
		System.out.println("[OK] Espacio físico creado -> id=" + saved.getEspId());
		return saved.getEspId();
	}

	// ===============================================================
	// v1.0 — Crear Curso usando getReferenceById (asignatura/docente)
	// ===============================================================
	@Transactional
	public Long crearCurso(Long asignaturaId, Long docenteId) {
		Curso cur = new Curso();
		cur.setCurNombre("Arquitecturas Empresariales - Grupo A");
		// Requerimiento del taller: getReferenceById (no hydrate completo)
		cur.setCurAsignatura(asignaturaRepo.getReferenceById(asignaturaId));
		cur.getCurDocentes().add(docenteRepo.getReferenceById(docenteId));

		Curso saved = cursoRepo.save(cur);
		System.out.println("[OK] Curso creado -> id=" + saved.getCurId());
		return saved.getCurId();
	}

	// =========================================================================
	// v1.0 — Crear Franja usando getReferenceById (curso/espacio/docente)
	// =========================================================================
	@Transactional
	public void crearFranja(Long cursoId, Long espacioId, Long docenteId) {
		FranjaHoraria fra = new FranjaHoraria();
		fra.setFraDia(DiaSemana.MIERCOLES);
		fra.setFraHoraInicio(LocalTime.of(10, 0));
		fra.setFraHoraFin(LocalTime.of(12, 0));

		fra.setFraCurso(cursoRepo.getReferenceById(cursoId));
		fra.setFraEspacioFisico(espacioRepo.getReferenceById(espacioId));
		fra.setFraDocente(docenteRepo.getReferenceById(docenteId));

		FranjaHoraria saved = franjaRepo.save(fra);
		System.out.println("[OK] Franja creada -> id=" + saved.getFraId());
	}

	// ===================================================================================
	// v0.5 — Consultar franjas por Curso (EAGER: curso + espacio + docente por
	// EntityGraph)
	// ===================================================================================
	@Transactional(readOnly = true)
	public void consultarFranjasPorCurso(Long cursoId) {
		System.out.println("== Franjas del curso " + cursoId + " ==");
		List<FranjaHoraria> franjas = franjaRepo.findByFraCurso_CurId(cursoId);
		for (FranjaHoraria f : franjas) {
			System.out.printf("- %s %s-%s | Curso: %s | Docente: %s %s | Espacio: %s%n",
					f.getFraDia(), f.getFraHoraInicio(), f.getFraHoraFin(),
					f.getFraCurso().getCurNombre(),
					f.getFraDocente().getPerNombres(), f.getFraDocente().getPerApellidos(),
					f.getFraEspacioFisico().getEspNombre());
		}
	}

	// ===================================================================================
	// v1.0 — Consultar franjas por Docente (EAGER solo curso; espacio LAZY hasta el
	// getter)
	// ===================================================================================
	@Transactional(readOnly = true)
	public void consultarFranjasPorDocente(Long docenteId) {
		System.out.println("== Franjas del docente " + docenteId + " ==");
		List<FranjaHoraria> franjas = franjaRepo.findByFraDocente_PerId(docenteId);
		for (FranjaHoraria f : franjas) {
			// 'fraCurso' ya viene EAGER por @EntityGraph; 'fraEspacioFisico' es LAZY (se
			// trae aquí):
			String espacio = f.getFraEspacioFisico().getEspNombre();
			System.out.printf("- %s %s-%s | Curso: %s | Espacio: %s%n",
					f.getFraDia(), f.getFraHoraInicio(), f.getFraHoraFin(),
					f.getFraCurso().getCurNombre(), espacio);
		}
	}

	// ================================================================
	// v0.5 — Eliminar Curso (cascade REMOVE -> borra sus franjas)
	// ================================================================
	@Transactional
	public void eliminarCursoEnCascada(Long cursoId) {
		long antes = franjaRepo.count();
		cursoRepo.deleteById(cursoId);
		long despues = franjaRepo.count();
		System.out.printf("[OK] Curso %d eliminado. Franjas antes=%d / después=%d%n",
				cursoId, antes, despues);
	}
}
