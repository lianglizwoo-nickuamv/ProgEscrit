package com.example.evaluacion160926;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Aquí está el cambio clave: apuntamos a Login.fxml dentro de tu paquete en resources
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/example/evaluacion160926/Login.fxml"));

        // Cargamos la escena con el tamaño adecuado para el Login
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        stage.setTitle("Sistema de Registro - Inicio de Sesión");
        stage.setScene(scene);
        stage.setResizable(false); // Para que no deformen la ventana
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}