package co.edu.unicauca.asae.taller05.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller05.domain.*;
import co.edu.unicauca.asae.taller05.repositories.*;

@Service
public class Taller05UseCases {

    private final DocenteRepository docenteRepo;
    private final AsignaturaRepository asignaturaRepo;
    private final CursoRepository cursoRepo;
    private final EspacioFisicoRepository espacioRepo;
    private final FranjaHorariaRepository franjaRepo;

    public Taller05UseCases(DocenteRepository docenteRepo, AsignaturaRepository asignaturaRepo,
            CursoRepository cursoRepo, EspacioFisicoRepository espacioRepo,
            FranjaHorariaRepository franjaRepo) {
        this.docenteRepo = docenteRepo;
        this.asignaturaRepo = asignaturaRepo;
        this.cursoRepo = cursoRepo;
        this.espacioRepo = espacioRepo;
        this.franjaRepo = franjaRepo;
    }

    /** v1.0: Crear Docente (+Oficina) con un solo save (cascade PERSIST). */
    @Transactional
    public Long crearDocenteConOficina() {
        Docente doc = new Docente();
        doc.setPerNombres("María");
        doc.setPerApellidos("Rojas");
        doc.setPerCorreo("maria.rojas@unicauca.edu.co");

        Oficina ofi = new Oficina();
        ofi.setOfiNombre("Oficina 3-201");
        ofi.setOfiUbicacion("Bloque 3 - Piso 2");

        doc.setDocOficina(ofi);
        Docente saved = docenteRepo.save(doc);
        System.out.println("[OK] Docente creado -> id=" + saved.getPerId()
                + " | oficina=" + saved.getDocOficina().getOfiNombre());
        return saved.getPerId();
    }

    /** Si no hay asignaturas, crea una. */
    @Transactional
    public Long asegurarAsignaturaBase() {
        List<Asignatura> todas = asignaturaRepo.findAll();
        if (!todas.isEmpty())
            return todas.get(0).getAsiId();
        Asignatura a = new Asignatura();
        a.setAsiNombre("Arquitectura de Software");
        a.setAsiCodigo("ASW-101");
        return asignaturaRepo.save(a).getAsiId();
    }

    /** Si no hay espacios, crea uno. */
    @Transactional
    public Long asegurarEspacioBase() {
        List<EspacioFisico> todos = espacioRepo.findAll();
        if (!todos.isEmpty())
            return todos.get(0).getEspId();
        EspacioFisico e = new EspacioFisico();
        e.setEspNombre("Aula Tulcán 101");
        e.setEspCapacidad(40);
        return espacioRepo.save(e).getEspId();
    }

    /** v1.0: Crear Curso con getReferenceById. */
    @Transactional
    public Long crearCurso(Long asignaturaId, Long docenteId) {
        Curso cur = new Curso();
        cur.setCurNombre("Arquitecturas Empresariales - Grupo A");
        cur.setCurAsignatura(asignaturaRepo.getReferenceById(asignaturaId));
        cur.getCurDocentes().add(docenteRepo.getReferenceById(docenteId));
        Long id = cursoRepo.save(cur).getCurId();
        System.out.println("[OK] Curso creado -> id=" + id);
        return id;
    }

    /** v1.0: Crear Franja con getReferenceById. */
    @Transactional
    public void crearFranja(Long cursoId, Long espacioId, Long docenteId) {
        FranjaHoraria fra = new FranjaHoraria();
        fra.setFraDia(DiaSemana.MIERCOLES);
        fra.setFraHoraInicio(LocalTime.of(10, 0));
        fra.setFraHoraFin(LocalTime.of(12, 0));
        fra.setFraCurso(cursoRepo.getReferenceById(cursoId));
        fra.setFraEspacioFisico(espacioRepo.getReferenceById(espacioId));
        Long id = franjaRepo.save(fra).getFraId();
        System.out.println("[OK] Franja creada -> id=" + id);
    }

    /**
     * v0.5: Consulta por curso (curso+espacio+docente EAGER por EntityGraph en
     * repo).
     */
    @Transactional(readOnly = true)
    public void consultarFranjasPorCurso(Long cursoId) {
        System.out.println("== Franjas del curso " + cursoId + " ==");
        for (FranjaHoraria f : franjaRepo.findByFraCurso_CurId(cursoId)) {
            System.out.printf("- %s %s-%s | Curso: %s | Docente: %s %s | Espacio: %s%n",
                    f.getFraDia(), f.getFraHoraInicio(), f.getFraHoraFin(),
                    f.getFraCurso().getCurNombre(),
                    f.getFraEspacioFisico().getEspNombre()); // aquí LAZY funciona porque hay sesión
        }
    }

    /** v1.0: Consulta por docente (curso EAGER; espacio LAZY hasta getter). */
    @Transactional(readOnly = true)
    public void consultarFranjasPorDocente(Long docenteId) {
        System.out.println("== Franjas del docente " + docenteId + " ==");
        franjaRepo.findByDocenteId(docenteId).forEach(f -> {
            String docentes = f.getFraCurso().getCurDocentes().stream()
                    .map(d -> d.getPerNombres() + " " + d.getPerApellidos())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("(sin docentes)");

            System.out.printf("- %s %s-%s | Curso: %s | Docentes: %s | Espacio: %s%n",
                    f.getFraDia(), f.getFraHoraInicio(), f.getFraHoraFin(),
                    f.getFraCurso().getCurNombre(), docentes,
                    f.getFraEspacioFisico().getEspNombre());
        });
    }

    /** v0.5: Eliminar curso (cascade REMOVE borra franjas). */
    @Transactional
    public void eliminarCursoEnCascada(Long cursoId) {
        long antes = franjaRepo.count();
        cursoRepo.deleteById(cursoId);
        long despues = franjaRepo.count();
        System.out.printf("[OK] Curso %d eliminado. Franjas antes=%d / después=%d%n",
                cursoId, antes, despues);
    }
}
