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

import java.io.IOException;
import java.util.Optional;

public class LoginController {

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
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        // Validación de campos vacíos
        if (usuario == null || usuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            // Uso de Alert de Advertencia (Requerimiento de la rúbrica)
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, ingrese el usuario y la contraseña.");
            alerta.showAndWait();
            return;
        }

        // Si la validación pasa, abrimos la ventana principal
        abrirVentanaPrincipal();
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