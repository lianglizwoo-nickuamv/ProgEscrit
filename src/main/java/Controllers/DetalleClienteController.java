package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cliente; // Importamos desde tu paquete model

public class DetalleClienteController {

    @FXML private Label lblNombreCompleto;
    @FXML private Label lblDetalles;
    @FXML private ImageView imgFoto;

    // Este es el método que recibe el objeto desde la ventana de Consulta
    public void cargarDatos(Cliente cliente) {
        lblNombreCompleto.setText(cliente.getNombres() + " " + cliente.getApellidos());

        String detalles = "Tipo: " + cliente.getTipoCliente() + "\n" +
                "Ciudad: " + cliente.getCiudad() + "\n" +
                "Fecha Nac.: " + cliente.getFechaNacimiento().toString() + "\n" +
                "Solicitud: " + cliente.getTipoSolicitud() + "\n" +
                "Servicios: " + cliente.getServiciosInteres();

        lblDetalles.setText(detalles);

        // Cargar la imagen si existe
        if (cliente.getRutaFotografia() != null && !cliente.getRutaFotografia().isEmpty()) {
            imgFoto.setImage(new Image(cliente.getRutaFotografia()));
        }
    }
}