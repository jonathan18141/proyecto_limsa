package pe.uch.intro_spring_mvc;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.uch.intro_spring_mvc.model.ActividadProceso;

@Controller
public class ProcesoMvcController {

	@GetMapping("/proceso")
    public String mostrarProceso(Model model) {
    	List<ActividadProceso> actividades = List.of(
    		    new ActividadProceso(1, "Acceso y validación en la plataforma web", "Personal de Planta", "COMPLETADO", "01 minuto"),
    		    new ActividadProceso(2, "Registro de entrada en el portal web", "Personal de Planta", "COMPLETADO", "02 minutos"),
    		    new ActividadProceso(3, "Verificación de marcaciones en la plataforma web", "Asistente de RRHH", "COMPLETADO", "10 minutos"),
    		    new ActividadProceso(4, "Consolidación web de tardanzas y faltas", "Analista de Personal", "EN PROCESO", "20 minutos"),
    		    new ActividadProceso(5, "Revisión de justificaciones digitales", "Supervisor de Planta", "PENDIENTE", "30 minutos"),
    		    new ActividadProceso(6, "Exportación de reporte web para planillas", "Jefe de Recursos Humanos", "PENDIENTE", "15 minutos")
    		);

        model.addAttribute("empresa", "B&B Limsa S.A.C.");
        model.addAttribute("area", "Recursos Humanos");
        model.addAttribute("proceso", "Automatización de asistencia");
        model.addAttribute("actividades", actividades);
        return "proceso";
    }
}