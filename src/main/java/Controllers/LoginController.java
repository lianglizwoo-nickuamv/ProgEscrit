package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import service.UsuarioService;
import utils.AlertHelper;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    private final UsuarioService usuarioService = UsuarioService.getInstance();

    // Vinculación de los componentes del FXML
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnIniciarSesion;

    // --- EVENTOS DE ACCIÓN (ActionEvent) ---

    @FXML
    void iniciarSesion(ActionEvent event) {
        validarYEntrar();
    }

    @FXML
    void crearCuenta(ActionEvent event) {
        abrirRegistroUsuario();
    }

    @FXML
    void salir(ActionEvent event) {
        // Uso de Alert de Confirmación (Requerimiento de la rúbrica)
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar salida");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que desea salir de la aplicación?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit(); // Cierra la aplicación correctamente
        }
    }

    // --- EVENTOS DE TECLADO (KeyEvent) ---

    @FXML
    void onEnterPressed(KeyEvent event) {
        // Requerimiento: Implementar funcionalidad con el teclado (ENTER para entrar)
        if (event.getCode() == KeyCode.ENTER) {
            validarYEntrar();
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void validarYEntrar() {
        String usuario = txtUsuario.getText() == null ? "" : txtUsuario.getText().trim();
        String password = txtPassword.getText();

        if (usuario.isEmpty() || password == null || password.isEmpty()) {
            AlertHelper.mostrarAdvertencia(
                    "Campos incompletos",
                    "Por favor, ingrese el usuario y la contraseña."
            );
            return;
        }

        if (!usuarioService.autenticar(usuario, password)) {
            AlertHelper.mostrarError(
                    "Acceso denegado",
                    "El usuario o la contraseña son incorrectos."
            );
            txtPassword.clear();
            txtPassword.requestFocus();
            return;
        }

        abrirVentanaPrincipal();
    }

    private void abrirRegistroUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/evaluacion160926/RegistroUsuario.fxml")
            );
            Parent root = loader.load();

            Stage registroStage = new Stage();
            registroStage.setTitle("Crear cuenta");
            registroStage.setScene(new Scene(root, 420, 410));
            registroStage.initOwner(btnIniciarSesion.getScene().getWindow());
            registroStage.initModality(Modality.WINDOW_MODAL);
            registroStage.setResizable(false);
            registroStage.showAndWait();

            RegistroUsuarioController controller = loader.getController();
            if (controller.getUsuarioRegistrado() != null) {
                txtUsuario.setText(controller.getUsuarioRegistrado());
                txtPassword.clear();
                txtPassword.requestFocus();
            }
        } catch (IOException e) {
            AlertHelper.mostrarError("Error", "No se pudo abrir la ventana para crear la cuenta.");
        }
    }

    private void abrirVentanaPrincipal() {
        try {
            // Cargamos la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Principal.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Sistema de Registro - Menú Principal");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

            // Cerramos la ventana actual del Login
            Stage loginStage = (Stage) btnIniciarSesion.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("No se pudo cargar la ventana principal.");
            alerta.showAndWait();
        }
    }
}
