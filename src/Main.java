
import controller.Admin;
import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.Ventana;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Globals.init(this);
        Ventana login = new Ventana("login", new Login(stage));
        login.start(stage);

    }

}
