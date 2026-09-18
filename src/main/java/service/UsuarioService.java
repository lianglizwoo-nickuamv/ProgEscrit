package service;

import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static final UsuarioService INSTANCIA = new UsuarioService();

    private final List<Usuario> usuarios = new ArrayList<>();

    private UsuarioService() {
    }

    public static UsuarioService getInstance() {
        return INSTANCIA;
    }

    public boolean registrarUsuario(String nombreUsuario, String password) {
        String usuarioNormalizado = normalizarUsuario(nombreUsuario);

        if (usuarioNormalizado.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        if (existeUsuario(usuarioNormalizado)) {
            return false;
        }

        usuarios.add(new Usuario(usuarioNormalizado, password));
        return true;
    }

    public boolean autenticar(String nombreUsuario, String password) {
        if (nombreUsuario == null || password == null) {
            return false;
        }

        String usuarioNormalizado = normalizarUsuario(nombreUsuario);

        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(usuarioNormalizado)
                    && usuario.passwordCoincide(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean existeUsuario(String nombreUsuario) {
        if (nombreUsuario == null) {
            return false;
        }

        String usuarioNormalizado = normalizarUsuario(nombreUsuario);

        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(usuarioNormalizado)) {
                return true;
            }
        }

        return false;
    }

    private String normalizarUsuario(String nombreUsuario) {
        return nombreUsuario == null ? "" : nombreUsuario.trim();
    }
}
