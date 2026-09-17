import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Optional;

public class PrincipalController {

    // --- EVENTOS DEL MENUBAR Y TOOLBAR ---

    @FXML
    void abrirRegistro(ActionEvent event) {
        // Llama a la ventana de registro
        abrirVentana("/ni/edu/uam/registro/views/Registro.fxml", "Registro de Nuevo Cliente");
    }

    @FXML
    void abrirConsulta(ActionEvent event) {
        // Llama a la ventana de consulta
        abrirVentana("/ni/edu/uam/registro/views/Consulta.fxml", "Consulta de Clientes");
    }

    @FXML
    void salir(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar salida");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que desea cerrar la sesión?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit(); // Finaliza la ejecución de JavaFX
        }
    }

    // --- EVENTOS DEL CONTEXT MENU ---

    @FXML
    void mostrarAcercaDe(ActionEvent event) {
        // Uso de Alert de Información (Requerimiento de la rúbrica)
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText("Sistema de Registro V1.0");
        alerta.setContentText("Proyecto práctico para la clase de Ingeniería en Sistemas.\n\nHaz clic derecho en la tabla de consulta más adelante para ver más opciones.");
        alerta.showAndWait();
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * Método utilitario para abrir nuevas ventanas sin repetir código.
     */
    private void abrirVentana(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

            // Opcional: Modality.APPLICATION_MODAL bloquea la ventana principal hasta que cerrés esta
            // stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de carga");
            alerta.setContentText("No se pudo abrir la ventana: " + titulo + "\nVerificá que el archivo FXML exista.");
            alerta.showAndWait();
        }
    }
}