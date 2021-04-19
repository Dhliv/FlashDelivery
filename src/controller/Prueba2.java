package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Prueba2 {

    
    void verConsulta(AnchorPane content, Object controlador){
        content.getChildren().clear();
        var loader = new FXMLLoader(getClass().getResource("../view/user.consulta.fxml"));
        loader.setController(controlador);
        Parent root;
        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
