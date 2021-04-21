package utilities;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ventana extends Application {
    private String ventana;
    private Object cont;

    public Ventana(String ventana, Object controlador) {
        this.ventana = ventana;
        this.cont = controlador;
    }

    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("../view/" + ventana + ".fxml"));
        loader.setController(cont);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle(ventana);
        stage.setScene(scene);
        stage.show();
    }
     
}