package utilities;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
  @Override public void start(Stage stage) throws Exception {
    Parent root = View.loadView(ventana, cont);
    Scene scene = new Scene(root);

    var a = new Image(this.getClass().getResourceAsStream("../resources/Images/icon.png"));
    stage.getIcons().add(a);
    /*JMetro jMetro = new JMetro(Style.LIGHT);
    jMetro.setScene(scene);*/

    stage.setTitle("Flash Delivery");
    stage.setResizable(false);
    stage.setMaximized(false);

    stage.setScene(scene);
    stage.show();
  }

}