package utilities;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ventana extends Application {
    private String ventana;
    private Object cont;
    private LoadView vista;

    public Ventana(String ventana, Object controlador) {
        this.ventana = ventana;
        this.cont = controlador;
        vista = new LoadView();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = vista.loadView(ventana, cont);
        Scene scene = new Scene(root);

        stage.setTitle(ventana);
        stage.setScene(scene);
        stage.show();
    }

}