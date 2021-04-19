
import Utilities.Ventana;
import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Ventana login = new Ventana("login", new LoginController(stage));
        login.start(stage);
    }

}
