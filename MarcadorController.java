package pe.uch.intro_spring_mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.uch.intro_spring_mvc.model.Empleado;
import pe.uch.intro_spring_mvc.model.RegistroAsistencia;
import pe.uch.intro_spring_mvc.repository.EmpleadoRepository;
import pe.uch.intro_spring_mvc.repository.RegistroAsistenciaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.Optional;

@Controller
public class MarcadorController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RegistroAsistenciaRepository asistenciaRepository;

    @GetMapping("/")
    public String inicio() {
        return "redirect:/marcador";
    }

    @GetMapping("/marcador")
    public String mostrarMarcador() {
        return "marcador-empleado";
    }

    // Ruta para el Panel de Administración / Reportes
    @GetMapping("/reporte")
    public String mostrarReporte(Model model) {
        model.addAttribute("asistencias", asistenciaRepository.findAll());
        model.addAttribute("empleados", empleadoRepository.findAll());
        model.addAttribute("empresa", "B&B LIMSA S.A.C.");
        model.addAttribute("area", "Recursos Humanos");
        return "asistencias-admin";
    }

    @PostMapping("/marcador/marcar")
    public String marcarAsistencia(@RequestParam("dni") String dni, RedirectAttributes redirect) {
        dni = dni != null ? dni.trim() : "";

        // Validar formato estricto de 8 dígitos numéricos
        if (!dni.matches("\\d{8}")) {
            redirect.addFlashAttribute("error", "El DNI debe tener exactamente 8 dígitos numéricos.");
            return "redirect:/marcador";
        }

        // Buscar empleado en la Base de Datos MySQL
        Optional<Empleado> empOpt = empleadoRepository.findById(dni);
        if (empOpt.isEmpty()) {
            redirect.addFlashAttribute("error", "El DNI " + dni + " no existe en el sistema.");
            return "redirect:/marcador";
        }

        Empleado emp = empOpt.get();
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        // Verificar si el trabajador ya tiene una marcación registrada el día de hoy
        Optional<RegistroAsistencia> registroHoyOpt = asistenciaRepository.findByDniAndFecha(dni, hoy);

        if (registroHoyOpt.isEmpty()) {
            // Primera marca del día -> ENTRADA
            RegistroAsistencia nuevoRegistro = new RegistroAsistencia();
            nuevoRegistro.setDni(dni);
            nuevoRegistro.setFecha(hoy);
            nuevoRegistro.setHoraEntrada(ahora);
            nuevoRegistro.setEstado("CONFORME");
            nuevoRegistro.setHorasTrabajadas(0.00);
            asistenciaRepository.save(nuevoRegistro);

            redirect.addFlashAttribute("mensaje", "Marcación registrada: " + emp.getNombre() + " [ENTRADA]");
        } else {
            // Ya marcó entrada hoy -> Registrar SALIDA
            RegistroAsistencia registro = registroHoyOpt.get();
            if (registro.getHoraSalida() != null) {
                redirect.addFlashAttribute("error", "El trabajador " + emp.getNombre() + " ya registró su salida el día de hoy.");
                return "redirect:/marcador";
            }

            registro.setHoraSalida(ahora);
            
            // Calcular horas trabajadas automáticamente (Corregido el error de sintaxis)
            Duration duracion = Duration.between(registro.getHoraEntrada(), ahora);
            double horas = duracion.toMinutes() / 60.0;
            registro.setHorasTrabajadas(Math.round(horas * 100.0) / 100.0);
            
            asistenciaRepository.save(registro);

            redirect.addFlashAttribute("mensaje", "Marcación registrada: " + emp.getNombre() + " [SALIDA]");
        }

        return "redirect:/marcador";
    }
}