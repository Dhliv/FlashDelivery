package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Clase encargada de gestionar las vistas dentro de una ventana
 * 
 * @author Julián Orejuela
 * @version 2.0, 18/09/2021
 */
public class View {
  private static Object referenceObject;
  private static Pane viewPane;
  private static Map<String, Parent> views;
  private static Boolean memory;

  /**
   * inicializar los atributos de la clase
   * 
   * @param obj objeto para acceder obj.getClass()
   */
  public static void init(Object obj) {
    referenceObject = obj;
    views = new LinkedHashMap<String, Parent>();
  }

  /**
   * establece el viewPane y el uso de la memoria
   * 
   * @param view
   * @param memory
   */
  public static void setViewPane(Pane view, Boolean memory) {
    viewPane = view;
    View.memory = memory;
  }

  // #---------------------------------------------------------------------------
  // # CORE
  // #---------------------------------------------------------------------------

  /**
   * cambiar la vista con controlador asignado
   * 
   * @param name    nombre del archivo fxml
   * @param control controlador a asignar
   */
  public static void cambiar(String name, Object control) {
    Parent view = views.get(name) == null ? loadView(name, control) : views.get(name);
    if (views.get(name) == null)
      views.put(name, memory == true ? view : null);
    cambiar(view);
  }

  /**
   * cargar vista desde FXML
   * 
   * @param name    nombre del archivo fxml
   * @param control control a asignar
   * @return theView
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

  // #---------------------------------------------------------------------------
  // # SOBRECARGAS
  // #---------------------------------------------------------------------------

  /**
   * cambiar la vista con controlador por defecto
   * 
   * @param name nombre del archivo fxml
   */
  public static void cambiar(String name) {
    cambiar(name, null);
  }

  /**
   * cargar vista desde FXML con controlador por defecto
   * 
   * @param name nombre del archivo fxml
   * @return theView
   */
  public static Parent loadView(String name) {
    return loadView(name, null);
  }

  // #---------------------------------------------------------------------------
  // # FUNCIONES EXTRAS
  // #---------------------------------------------------------------------------

  /**
   * cambiar la vista internamente
   * 
   * @param view
   */
  private static void cambiar(Parent view) {
    viewPane.getChildren().clear();
    viewPane.getChildren().add(view);
  }

  /**
   * Carga una vista completamente nueva para el rightContent.
   * 
   * @param name       Nombre de la ventana a cargar.
   * @param controller Controlador de la ventana a cargar.
   */
  public static void newView(String name, Object controller) {
    Parent root = loadView(name, controller);
    cambiar(root);
  }

  /**
   * limpiar la memoria de las vistas
   */
  public static void clearViews() {
    views.clear();
  }

  public static Pane getViewPane() {
    return viewPane;
  }
}
