package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Empleado;
import utilities.*;

public class UserConsulta implements Initializable {

  @FXML private TableView<Empleado> tableUsers;
  @FXML private TableColumn<Empleado, Integer> cedula;
  @FXML private TableColumn<Empleado, String> nombre;
  @FXML private TableColumn<Empleado, String> apellido;
  @FXML private TableColumn<Empleado, Integer> sede;
  @FXML private TableColumn<Empleado, String> rol;
  @FXML private TableColumn<Empleado, String> direccion;
  @FXML private TableColumn<Empleado, String> telefono;
  @FXML private TableColumn<Empleado, LocalDate> birthdate;

  /**
   * Inicializa los datos de la tabla de empleados.
   * 
   * @param location  not used.
   * @param resources not used.
   */
  @Override public void initialize(URL location, ResourceBundle resources) {
    cedula.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("cedula"));
    nombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombres"));
    apellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidos"));
    sede.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("sede"));
    rol.setCellValueFactory(new PropertyValueFactory<Empleado, String>("rol"));
    direccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
    telefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
    birthdate.setCellValueFactory(new PropertyValueFactory<Empleado, LocalDate>("birthdate"));
    ObservableList<Empleado> s = FXCollections.observableArrayList();
    s.addAll(Empleado.getSedes());

    tableUsers.setItems(s);
  }

  @FXML void borrar(ActionEvent event) {

  }

  /**
   * Accede a la pestaña de registro de usuarios.
   * 
   * @param event not used.
   */
  @FXML void goToUsuariosRegistro(ActionEvent event) {
    Globals.cambiarVista(Globals.loadView("user.register", new UserRegister(this)));
  }

  /**
   * Accede a la pestaña de edicion de empleado segúm el empleado seleccionado.
   * 
   * @param event not used.
   */
  @FXML void userEditButton(ActionEvent event) {
    Empleado e = tableUsers.getSelectionModel().getSelectedItem();
    if (e != null)
      Globals.cambiarVista(Globals.loadView("user.edit", new UserEdit(e, this)));
    else
      GeneralAlerts.showUserNullAlert();
  }
}
