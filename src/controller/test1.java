package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class test1 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private AnchorPane content;

    @FXML
    void goToSedeConsulta(ActionEvent event) {
        content.getChildren().clear();
        var loader = new FXMLLoader(getClass().getResource("../view/sede.consulta.fxml"));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goToUsuariosConsulta(ActionEvent event) {
        content.getChildren().clear();
        var loader = new FXMLLoader(getClass().getResource("../view/user.consulta.fxml"));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goToAdminReportes(ActionEvent event) {
        content.getChildren().clear();
        var loader = new FXMLLoader(getClass().getResource("../view/reportes.fxml"));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goToUsuariosRegistro(ActionEvent event) {
        content.getChildren().clear();
        var loader = new FXMLLoader(getClass().getResource("../view/user.register.fxml"));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clicksoide(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Does it works?", "Test", 0);
    }

    @FXML
    void initialize() {
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'Untitled'.";
        assert x2 != null : "fx:id=\"x2\" was not injected: check your FXML file 'Untitled'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'Untitled'.";
        assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'Untitled'.";

    }
}
