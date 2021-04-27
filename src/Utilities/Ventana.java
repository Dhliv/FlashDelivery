package utilities;

import javafx.application.Application;
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
        Parent root = Globals.loadView(ventana, cont);
        Scene scene = new Scene(root);

        stage.setTitle(ventana);
        stage.setScene(scene);
        stage.show();
    }

}