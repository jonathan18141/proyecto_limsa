package pe.uch.intro_spring_mvc.model;

public class ActividadProceso {
    private int orden;
    private String nombre;
    private String responsable;
    private String estado;
    private String tiempoEstimado;

    public ActividadProceso(int orden, String nombre, String responsable, String estado, String tiempoEstimado) {
        this.orden = orden;
        this.nombre = nombre;
        this.responsable = responsable;
        this.estado = estado;
        this.tiempoEstimado = tiempoEstimado;
    }

    public int getOrden() { return orden; }
    public String getNombre() { return nombre; }
    public String getResponsable() { return responsable; }
    public String getEstado() { return estado; }
    public String getTiempoEstimado() { return tiempoEstimado; }
}