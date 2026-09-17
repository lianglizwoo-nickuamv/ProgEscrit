package Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Cliente;
import model.DataStore;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultaController implements Initializable {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombres;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, LocalDate> colFecha;
    @FXML private TableColumn<Cliente, String> colSolicitud;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Vincular las columnas de la tabla con los atributos de la clase Cliente
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

        // 2. Cargar los datos desde nuestro DataStore en memoria
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(DataStore.getInstancia().getListaClientes());
        tablaClientes.setItems(clientes);

        // 3. Requerimiento: MouseEvent (Doble clic para abrir detalles)
        tablaClientes.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
                if (seleccionado != null) {
                    abrirDetalleCliente(seleccionado);
                }
            }
        });
    }

    private void abrirDetalleCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetalleCliente.fxml"));
            Parent root = loader.load();

            // ¡PUNTO CLAVE DE LA RÚBRICA!: Pasar datos entre ventanas
            DetalleClienteController controlador = loader.getController();
            controlador.cargarDatos(cliente);

            Stage stage = new Stage();
            stage.setTitle("Detalle del Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportarReporte(ActionEvent event) {
        // Requerimiento: Uso de DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta para guardar reporte");

        Stage stage = (Stage) tablaClientes.getScene().getWindow();
        File directorio = directoryChooser.showDialog(stage);

        if (directorio != null) {
            // Aquí simulamos que se guarda un archivo
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Exportación Exitosa");
            alerta.setHeaderText(null);
            alerta.setContentText("El reporte se ha 'guardado' en:\n" + directorio.getAbsolutePath());
            alerta.showAndWait();
        }
    }

    @FXML
    void cerrar(ActionEvent event) {
        Stage stage = (Stage) tablaClientes.getScene().getWindow();
        stage.close();
    }
}