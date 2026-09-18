package model;

public class Usuario {

    private final String nombreUsuario;
    private final String password;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean passwordCoincide(String passwordIngresado) {
        return password.equals(passwordIngresado);
    }
}
