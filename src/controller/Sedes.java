package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Sede;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import utilities.GeneralChecker;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.View;

/**
 * Controlador para las vistas sede.consulta, sede.register.edit
 * @author Julián Orejuela
 * @version 2.0, 21/09/2021
 */
public class Sedes {
    @FXML private TableView<Sede> sedesTable;
    @FXML private TableColumn<Sede, Integer> id_column;
    @FXML private TableColumn<Sede, String> nombre_column;
    @FXML private TableColumn<Sede, String> direccion_column;
    public TextField direccion;
    public TextField nombre;

    public void initialize() {
        if (sedesTable != null) {
            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            nombre_column.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            direccion_column.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            sedesTable.setItems(Sede.getSedesList());
        }
    }

    /**
     * go to registrar una nueva sede
     */
    public void registrar(ActionEvent event) {
        View.cambiar("sede.register.edit");
    }

    public void editar(ActionEvent event) {
        Sede sede = sedesTable.getSelectionModel().getSelectedItem();
        /*if(sede)*/
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