package utilities;

import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import controller.Login;

public class Globals {
  public static Stage pantalla;
  public static JMetro style;
  private static Ventana ventana;

  public static void init() {
    style = new JMetro();
    style.setStyle(Style.LIGHT);
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

}
