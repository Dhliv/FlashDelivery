package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Peticion;
import model.Entities.Empleado;
import utilities.Globals;

/**
 * Controlador para la vista auxiliar.fxml
 * 
 * @author Julián Orejuela
 * @version 1.0, 27/09/2021
 */
public class Auxiliar {
  @FXML
  private TableView<Peticion.FormattedData> recogerTable;
  @FXML
  private TableColumn<Peticion.FormattedData, Integer> id;
  @FXML
  private TableColumn<Peticion.FormattedData, String> descripcion;
  @FXML
  private TableColumn<Peticion.FormattedData, String> nombre;
  @FXML
  private TableColumn<Peticion.FormattedData, String> ciudad;
  @FXML
  private TableColumn<Peticion.FormattedData, String> direccion;
  @FXML
  private TableColumn<Peticion.FormattedData, String> telefono;
  @FXML
  private Label labelPOS;
  @FXML
  private Label labelUsuario;
  private Empleado user;

  public Auxiliar(Empleado user) {
    this.user = user;
  }

  public void initialize() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
    direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    recogerTable.setItems(Peticion.getPeticionesFormattedData(user.getCedula()));

    labelUsuario.setText("Bienvenido \n" + user.getNombres());
  }

  @FXML
  void logOut(ActionEvent event) {
    Globals.logOut();
  }

}