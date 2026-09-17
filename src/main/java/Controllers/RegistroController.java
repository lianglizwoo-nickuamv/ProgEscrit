package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Cliente;
import model.DataStore;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private ComboBox<String> cmbTipoCliente;
    @FXML private ComboBox<String> cmbCiudad;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private RadioButton rbNuevo;
    @FXML private RadioButton rbRenovacion;
    @FXML private ToggleGroup tgSolicitud;
    @FXML private CheckBox chkSoporte;
    @FXML private CheckBox chkGarantia;
    @FXML private ImageView imgFotografia;

    private String rutaImagenSeleccionada = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Llenar los ComboBox al cargar la ventana
        cmbTipoCliente.getItems().addAll("Individual", "Corporativo", "Gubernamental");
        cmbCiudad.getItems().addAll("Managua", "León", "Granada", "Masaya", "Estelí");

        // Seleccionamos un RadioButton por defecto
        rbNuevo.setSelected(true);
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Fotografía del Cliente");
        // Filtro para mostrar solo imágenes
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        // Obtenemos la ventana actual para mostrar el diálogo
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        File archivo = fileChooser.showOpenDialog(stage);

        if (archivo != null) {
            rutaImagenSeleccionada = archivo.toURI().toString();
            Image imagen = new Image(rutaImagenSeleccionada);
            imgFotografia.setImage(imagen);
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        // 1. Validaciones básicas (Requerimiento de la rúbrica)
        if (txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
                cmbTipoCliente.getValue() == null || cmbCiudad.getValue() == null ||
                dpFechaNacimiento.getValue() == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, complete todos los campos obligatorios.");
            alerta.showAndWait();
            return;
        }

        // 2. Extraer datos de los controles especiales
        String tipoSolicitud = rbNuevo.isSelected() ? "Nuevo" : "Renovación";

        StringBuilder servicios = new StringBuilder();
        if (chkSoporte.isSelected()) servicios.append("Soporte ");
        if (chkGarantia.isSelected()) servicios.append("Garantía");

        // 3. Crear el objeto Cliente
        Cliente nuevoCliente = new Cliente(
                txtNombres.getText(),
                txtApellidos.getText(),
                cmbTipoCliente.getValue(),
                cmbCiudad.getValue(),
                dpFechaNacimiento.getValue(),
                tipoSolicitud,
                servicios.toString().trim(),
                rutaImagenSeleccionada
        );

        // 4. Guardar en memoria usando nuestro Singleton
        DataStore.getInstancia().agregarCliente(nuevoCliente);

        // 5. Confirmación de éxito
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText("Cliente registrado correctamente.");
        alerta.showAndWait();

        limpiar(null); // Limpiamos el formulario para el siguiente registro
    }

    @FXML
    void limpiar(ActionEvent event) {
        txtNombres.clear();
        txtApellidos.clear();
        cmbTipoCliente.setValue(null);
        cmbCiudad.setValue(null);
        dpFechaNacimiento.setValue(null);
        rbNuevo.setSelected(true);
        chkSoporte.setSelected(false);
        chkGarantia.setSelected(false);
        imgFotografia.setImage(null);
        rutaImagenSeleccionada = "";
    }

    @FXML
    void cancelar(ActionEvent event) {
        // Cerramos la ventana actual
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }
}