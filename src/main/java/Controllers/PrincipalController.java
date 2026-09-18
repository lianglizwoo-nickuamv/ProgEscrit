package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PrincipalController {

    @FXML
    private VBox areaCentral;

    @FXML
    private void initialize() {
        ContextMenu menuContextual = new ContextMenu();

        MenuItem opcionRegistrar = new MenuItem("Registrar cliente");
        opcionRegistrar.setOnAction(this::abrirRegistro);

        MenuItem opcionConsultar = new MenuItem("Consultar clientes");
        opcionConsultar.setOnAction(this::abrirConsulta);

        MenuItem opcionAcercaDe = new MenuItem("Acerca del sistema");
        opcionAcercaDe.setOnAction(this::mostrarAcercaDe);

        menuContextual.getItems().addAll(
                opcionRegistrar,
                opcionConsultar,
                new SeparatorMenuItem(),
                opcionAcercaDe
        );

        areaCentral.setOnContextMenuRequested(event -> {
            menuContextual.show(
                    areaCentral,
                    event.getScreenX(),
                    event.getScreenY()
            );

            event.consume();
        });

        menuContextual.setAutoHide(true);
    }

    @FXML
    void abrirRegistro(ActionEvent event) {
        abrirVentana(
                "/views/Registro.fxml",
                "Registro de Nuevo Cliente"
        );
    }

    @FXML
    void abrirConsulta(ActionEvent event) {
        abrirVentana(
                "/views/Consulta.fxml",
                "Consulta de Clientes"
        );
    }

    @FXML
    void mostrarAcercaDe(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText("Sistema de Registro V1.0");
        alerta.setContentText(
                "Sistema para registrar y consultar clientes.\n\n"
                        + "Utilizá el menú superior, la barra de herramientas "
                        + "o hacé clic derecho en el área central."
        );
        alerta.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar salida");
        alerta.setHeaderText(null);
        alerta.setContentText(
                "¿Está seguro que desea salir de la aplicación?"
        );

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void abrirVentana(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(rutaFxml)
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de carga");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "No se pudo abrir la ventana: " + titulo
            );
            alerta.showAndWait();
        }
    }
}
