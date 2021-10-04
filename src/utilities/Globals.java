package utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;
import java.util.HashMap;

import controller.Login;

public class Globals {
  public static Stage pantalla;
  private static Ventana ventana;
  private static model.RegistrarEnvio envio;
  private static Boolean REGISTRARENVIO;

  public static void setEnvio(model.RegistrarEnvio r) {
    envio = r;
  }

  public static model.RegistrarEnvio getEnvio() {
    return envio;
  }

  /**
   * Numero "value" es redondeado a "places" cifras decimales
   * 
   * @param value numero double a redondear
   * @param place numero de cifras
   * @return value formateado.
   */
  public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }

  // ! --------------------------------------------
  // ! POR BORRAR

  private static Object referenceObject;
  public static Pane viewPane;
  private static Map<String, Parent> views;

  public static void init(Object obj) {
    referenceObject = obj;
    views = new HashMap<String, Parent>();
  }

  public static void cambiarVista(String name) {
    cambiarVista(name, null);
  }

  public static void cambiarVista(String name, Object control) {
    if (views.get(name) == null) {
      Parent view = loadView(name, control);
      views.put(name, view);
      cambiarVista(view);
    } else {
      Parent t = views.get(name);
      cambiarVista(t);
    }
  }

  public static void cambiarVista(Parent view) {
    viewPane.getChildren().clear();
    viewPane.getChildren().add(view);
  }

  public static Parent loadView(String name) {
    return loadView(name, null);
  }

  /**
   * Carga vista desde FXML
   * 
   * @param name    filename
   * @param control control a asignar a la vista
   * @return la vista
   */
  public static Parent loadView(String name, Object control) {
    FXMLLoader loader = new FXMLLoader(referenceObject.getClass().getResource("view/" + name + ".fxml"));
    if (control != null)
      loader.setController(control);
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return root;
  }

  public static Scene loadScene(String name) {
    return new Scene(loadView(name, null));
  }

  // ! --------------------------------------------

  /**
   * Desconecta al usuario actual del sistema.
   */
  public static void logOut() {
    pantalla.close();
    View.clearViews();
    ventana = new Ventana("login", new Login());
    try {
      ventana.start(pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Retorna el valor de REGISTRARENVIO. Esta variable se usa para cambiar entre
   * la vista OperadorRecoger y RecogerPaquete
   * 
   * @return
   */
  public static boolean getRegistrarEnvio() {
    return REGISTRARENVIO;
  }

  /**
   * Setea la variable REGISTRARENVIO con el dato que se manda por parametro.
   * 
   * @param b Booleano que se manda por parametro.
   */
  public static void setRegistarEnvio(boolean b) {
    REGISTRARENVIO = b;
  }

}
