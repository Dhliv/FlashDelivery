
import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Ventana;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Ventana login = new Ventana("login", new Login(stage));
        login.start(stage);
    }

}
