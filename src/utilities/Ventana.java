package utilities;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.*;

public class Ventana extends Application {
  private String ventana;
  private Object cont;

  /**
   * Constructor de la clase Ventana.
   * 
   * @param ventana     nombre de la ventana que se desea ejecutar.
   * @param controlador controlador de la ventana que se desea ejecutar.
   */
  public Ventana(String ventana, Object controlador) {
    this.ventana = ventana;
    this.cont = controlador;
  }

  /**
   * Inicializa una nueva ventana.
   */
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = View.loadView(ventana, cont);
    Scene scene = new Scene(root);
    // JMetro jmetro = new JMetro(Style.LIGHT);
    // jmetro.setScene(scene);
    // jmetro.setParent(parent);
    stage.setTitle(ventana);

    stage.setScene(scene);
    stage.show();
  }

}