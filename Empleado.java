package pe.uch.intro_spring_mvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajadores")
public class Empleado {

    @Id
    private String dni;
    private String nombre;
    private String area;
    private String ubicacion;
    private String turno;

    public Empleado() {}

    public Empleado(String dni, String nombre, String area, String ubicacion, String turno) {
        this.dni = dni;
        this.nombre = nombre;
        this.area = area;
        this.ubicacion = ubicacion;
        this.turno = turno;
    }

    // Getters y Setters tradicionales
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    // METODO PUENTE: Permite que Thymeleaf lea 'turnoAsignado' sin romper tu HTML original
    public String getTurnoAsignado() {
        return this.turno;
    }
}