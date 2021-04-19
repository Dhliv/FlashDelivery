package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.ModuleLayer.Controller;

import controller.*;

public class Ventanas extends Application {
    private String ventana;
    private Object cont;

    public Ventanas(String ventana, Object controlador) {
        this.ventana = ventana;
        this.cont = controlador;
        launch(new String[] {});
    }

    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource(ventana + ".fxml"));
        loader.setController(cont);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}