package controller;

import java.net.URL;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Sede;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import utilities.GeneralChecker;
import utilities.GeneralString;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.View;

/**
 * Controlador para las vistas sede.consulta, sede.register.edit
 * 
 * @author Julián Orejuela
 * @version 2.1, 23/09/2021
 */
public class Sedes {
  @FXML
  private TableView<Sede> sedesTable;
  @FXML
  private TableColumn<Sede, Integer> id_column;
  @FXML
  private TableColumn<Sede, String> nombre_column;
  @FXML
  private TableColumn<Sede, String> direccion_column;
  @FXML
  private JFXTextField nombre;
  @FXML
  private JFXTextField direccion;
  @FXML
  private URL location;
  private Sede sede;

  public void initialize() {
    Globals.style.setParent(sedesTable);
    if (GeneralString.getName(location).equals("sede.consulta")) {
      id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
      nombre_column.setCellValueFactory(new PropertyValueFactory<>("nombre"));
      direccion_column.setCellValueFactory(new PropertyValueFactory<>("direccion"));
      sedesTable.setItems(Sede.getSedesList());
    }
    if (sede != null) {
      nombre.setText(sede.nombre);
      direccion.setText(sede.direccion);
    }
  }

  /**
   * go to registrar una nueva sede
   */
  public void registrar(ActionEvent event) {
    View.cambiar("sede.register.edit", new Sedes());
  }

  /**
   * go to editar sede seleccionada
   */
  public void editar(ActionEvent event) {
    sede = sedesTable.getSelectionModel().getSelectedItem();
    if (sede != null)
      View.cambiar("sede.register.edit", this);
    else
      SpecificAlerts.showUserNullAlert();
  }

  /**
   * go back button
   */
  public void atras(ActionEvent event) {
    View.cambiar("sede.consulta");
  }

  /**
   * guardar el registro de una nueva sede o la edición de una
   */
  public void guardar(ActionEvent event) {
    if (!GeneralChecker.checkTextFieldEmptyAndFC(new TextField[] { nombre, direccion }))
      return;
    model.Entities.Sede.upsert(sede, nombre.getText(), direccion.getText());
    SpecificAlerts.showRegSuccess();
    View.cambiar("sede.consulta");
  }
}