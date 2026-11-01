package pe.uch.intro_spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.uch.intro_spring_mvc.model.RegistroAsistencia;
import java.time.LocalDate;
import java.util.Optional;

public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {
    Optional<RegistroAsistencia> findByDniAndFecha(String dni, LocalDate fecha);
}