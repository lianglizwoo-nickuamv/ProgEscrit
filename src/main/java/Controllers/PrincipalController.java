package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PrincipalController {

    @FXML
    void abrirRegistro(ActionEvent event) {

        abrirVentana("/views/Registro.fxml", "Registro de Nuevo Cliente");
    }

    @FXML
    void abrirConsulta(ActionEvent event) {

        abrirVentana("/views/Consulta.fxml", "Consulta de Clientes");
    }

    @FXML
    void salir(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar salida");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que desea cerrar la sesión?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    void mostrarAcercaDe(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText("Sistema de Registro V1.0");
        alerta.setContentText("Proyecto práctico para la clase de Ingeniería en Sistemas.\n\nHaz clic derecho en la tabla de consulta más adelante para ver más opciones.");
        alerta.showAndWait();
    }

    private void abrirVentana(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de carga");
            alerta.setContentText("No se pudo abrir la ventana: " + titulo + "\nVerificá que el archivo FXML exista en la ruta correcta.");
            alerta.showAndWait();
        }
    }
}