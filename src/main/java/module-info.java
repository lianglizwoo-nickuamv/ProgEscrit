module com.example.evaluacion160926 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.evaluacion160926 to javafx.fxml;
    exports com.example.evaluacion160926;

    // 1. Darle permiso a JavaFX para leer tus controladores
    opens Controllers to javafx.fxml;
    exports Controllers;

    // 2. Darle permiso a JavaFX para leer tus modelos
    // (¡Súper importante para que el TableView de la ventana Consulta no te dé error más adelante!)
    opens model to javafx.base;
    exports model;
}