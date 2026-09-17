package model;
import java.time.LocalDate;

public class Cliente {

    private String nombres;
    private String apellidos;
    private String tipoCliente; // Viene del ComboBox
    private String ciudad; // Viene del ComboBox
    private LocalDate fechaNacimiento; // Viene del DatePicker
    private String tipoSolicitud; // Viene del ToggleGroup/RadioButton
    private String serviciosInteres; // Viene de los CheckBox (podemos concatenarlos en un String)
    private String rutaFotografia; // La ruta absoluta de la imagen seleccionada por el FileChooser

    // Constructor vacío (siempre es buena práctica tenerlo)
    public Cliente() {
    }

    // Constructor con todos los parámetros
    public Cliente(String nombres, String apellidos, String tipoCliente, String ciudad,
                   LocalDate fechaNacimiento, String tipoSolicitud,
                   String serviciosInteres, String rutaFotografia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoCliente = tipoCliente;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = serviciosInteres;
        this.rutaFotografia = rutaFotografia;
    }

    // --- Getters y Setters ---

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getServiciosInteres() {
        return serviciosInteres;
    }

    public void setServiciosInteres(String serviciosInteres) {
        this.serviciosInteres = serviciosInteres;
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }

    // Un método toString nos puede servir para hacer debug en la consola
    @Override
    public String toString() {
        return nombres + " " + apellidos + " - " + tipoCliente + " (" + ciudad + ")";
    }
}
