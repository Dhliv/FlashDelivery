package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(new test1());

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}