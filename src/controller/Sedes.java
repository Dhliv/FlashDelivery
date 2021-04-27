package controller;

import org.postgresql.hostchooser.GlobalHostStatusTracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import utilities.Globals;

public class Sedes {

    @FXML
    TextField direccion;

    public TextField nombre;

    public void registrar(ActionEvent event) {
        Globals.adminViewPane.getChildren().clear();
        Globals.adminViewPane.getChildren().add(Globals.loadView("sede.register.edit"));
    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void borrar(ActionEvent event) {

    }

    @FXML
    void atras(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {
        model.Sedes.createSede(nombre.getText(), direccion.getText());
    }

}