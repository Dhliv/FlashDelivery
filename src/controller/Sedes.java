package controller;

import org.postgresql.hostchooser.GlobalHostStatusTracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import utilities.Globals;

public class Sedes {
    public TextField direccion;
    public TextField nombre;

    public void registrar(ActionEvent event) {
        Globals.cambiarVista("sede.register.edit");
    }

    public void editar(ActionEvent event) {

    }

    public void borrar(ActionEvent event) {

    }

    public void atras(ActionEvent event) {
        Globals.cambiarVista("sede.consulta");
    }

    public void guardar(ActionEvent event) {
        model.Sedes.createSede(nombre.getText(), direccion.getText());
    }
}