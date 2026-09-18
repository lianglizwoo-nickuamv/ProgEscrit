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

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private ComboBox<String> cmbTipoCliente;

    @FXML
    private ComboBox<String> cmbCiudad;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private RadioButton rbNuevo;

    @FXML
    private RadioButton rbRenovacion;

    @FXML
    private ToggleGroup tgSolicitud;

    @FXML
    private CheckBox chkSoporte;

    @FXML
    private CheckBox chkGarantia;

    @FXML
    private ImageView imgFotografia;

    private String rutaImagenSeleccionada = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbTipoCliente.getItems().addAll(
                "Individual",
                "Corporativo",
                "Gubernamental"
        );

        cmbCiudad.getItems().addAll(
                "Managua",
                "León",
                "Granada",
                "Masaya",
                "Estelí"
        );

        rbNuevo.setSelected(true);
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Fotografía del Cliente");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

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
        if (txtNombres.getText().isEmpty()
                || txtApellidos.getText().isEmpty()
                || cmbTipoCliente.getValue() == null
                || cmbCiudad.getValue() == null
                || dpFechaNacimiento.getValue() == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Por favor, complete todos los campos obligatorios."
            );
            alerta.showAndWait();
            return;
        }

        if (dpFechaNacimiento.getValue().isAfter(LocalDate.now())) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Fecha no válida");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "La fecha de nacimiento no puede ser posterior a hoy."
            );
            alerta.showAndWait();

            dpFechaNacimiento.requestFocus();
            return;
        }

        String tipoSolicitud = rbNuevo.isSelected()
                ? "Nuevo"
                : "Renovación";

        StringBuilder servicios = new StringBuilder();

        if (chkSoporte.isSelected()) {
            servicios.append("Soporte ");
        }

        if (chkGarantia.isSelected()) {
            servicios.append("Garantía");
        }

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

        DataStore.getInstancia().agregarCliente(nuevoCliente);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText("Cliente registrado correctamente.");
        alerta.showAndWait();

        limpiar(null);
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
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }
}
