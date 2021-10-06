import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.Ventana;
import utilities.View;

public class Main extends Application {
  public static void main(String[] args) {
    System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
    launch(args);
  }

  public void start(Stage stage) throws Exception {

    View.init(this);
    Globals.init();
    SpecificAlerts.init();
    Globals.pantalla = stage;

    Ventana login = new Ventana("login", new Login());
    login.start(stage);
  }
}
