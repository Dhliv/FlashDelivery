package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXLogin extends Application{
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.setController(new LoginController());
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 275);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
        System.out.println("XD");
    }
}
