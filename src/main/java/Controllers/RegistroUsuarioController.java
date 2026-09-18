package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UsuarioService;
import utils.AlertHelper;

public class RegistroUsuarioController {

    @FXML
    private TextField txtNuevoUsuario;

    @FXML
    private PasswordField txtNuevaPassword;

    @FXML
    private PasswordField txtConfirmarPassword;

    private final UsuarioService usuarioService = UsuarioService.getInstance();
    private String usuarioRegistrado;

    @FXML
    void registrarCuenta(ActionEvent event) {
        String usuario = txtNuevoUsuario.getText() == null
                ? ""
                : txtNuevoUsuario.getText().trim();
        String password = txtNuevaPassword.getText() == null
                ? ""
                : txtNuevaPassword.getText();
        String confirmacion = txtConfirmarPassword.getText() == null
                ? ""
                : txtConfirmarPassword.getText();

        if (usuario.isEmpty() || password.isEmpty() || confirmacion.isEmpty()) {
            AlertHelper.mostrarAdvertencia(
                    "Campos incompletos",
                    "Complete todos los campos para crear la cuenta."
            );
            return;
        }

        if (usuario.length() < 4 || usuario.length() > 20) {
            AlertHelper.mostrarAdvertencia(
                    "Usuario no válido",
                    "El usuario debe tener entre 4 y 20 caracteres."
            );
            return;
        }

        if (!usuario.matches("[\\p{L}\\p{N}_]+")) {
            AlertHelper.mostrarAdvertencia(
                    "Usuario no válido",
                    "Use solamente letras, números y guion bajo; no use espacios."
            );
            return;
        }

        if (password.length() < 6 || password.length() > 30) {
            AlertHelper.mostrarAdvertencia(
                    "Contraseña no válida",
                    "La contraseña debe tener entre 6 y 30 caracteres."
            );
            return;
        }

        if (password.contains(" ")) {
            AlertHelper.mostrarAdvertencia(
                    "Contraseña no válida",
                    "La contraseña no puede contener espacios."
            );
            return;
        }

        if (!password.equals(confirmacion)) {
            AlertHelper.mostrarAdvertencia(
                    "Contraseñas diferentes",
                    "La contraseña y su confirmación no coinciden."
            );
            txtConfirmarPassword.clear();
            txtConfirmarPassword.requestFocus();
            return;
        }

        if (!usuarioService.registrarUsuario(usuario, password)) {
            AlertHelper.mostrarAdvertencia(
                    "Usuario existente",
                    "Ya existe una cuenta con ese nombre de usuario."
            );
            txtNuevoUsuario.requestFocus();
            return;
        }

        usuarioRegistrado = usuario;
        AlertHelper.mostrarInformacion(
                "Cuenta creada",
                "La cuenta fue creada correctamente. Ya puede iniciar sesión."
        );
        cerrarVentana();
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentana();
    }

    public String getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNuevoUsuario.getScene().getWindow();
        stage.close();
    }
}
