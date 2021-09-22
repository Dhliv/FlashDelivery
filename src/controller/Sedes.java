package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import utilities.GeneralChecker;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.View;

public class Sedes {
    public TextField direccion;
    public TextField nombre;

    /**
     * go to registrar una nueva sede
     */
    public void registrar(ActionEvent event) {
        View.cambiar("sede.register.edit");
    }

    public void editar(ActionEvent event) {

    }

    public void borrar(ActionEvent event) {

    }

    /**
     * go back button
     */
    public void atras(ActionEvent event) {
        View.cambiar("sede.consulta");
    }

    /**
     * registrar una nueva sede en la db
     */
    public void guardar(ActionEvent event) {
        if (!GeneralChecker.checkTextFieldEmptyAndFC(new TextField[] { nombre, direccion })) return;
        model.Entities.Sede.registrar(nombre.getText(), direccion.getText());
        SpecificAlerts.showRegSuccess();
        View.cambiar("sede.consulta");
    }
}