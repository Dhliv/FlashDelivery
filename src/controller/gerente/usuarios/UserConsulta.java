package controller.gerente.usuarios;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Empleado;
import model.Entities.Usuario;
import utilities.*;

// TODO Documentar.
public class UserConsulta {
  @FXML
  private Button btBorrar;
  @FXML
  private TableView<Empleado> tableUsers;
  @FXML
  private TableColumn<Empleado, Integer> cedula;
  @FXML
  private TableColumn<Empleado, String> nombre;
  @FXML
  private TableColumn<Empleado, String> apellido;
  @FXML
  private TableColumn<Empleado, Integer> sede;
  @FXML
  private TableColumn<Empleado, String> rol;
  @FXML
  private TableColumn<Empleado, String> direccion;
  @FXML
  private TableColumn<Empleado, String> telefono;
  @FXML
  private TableColumn<Empleado, LocalDate> birthdate;
  @FXML
  private Button btChange;
  private boolean borrar;

  /**
   * Inicializa los datos de la tabla de empleados.
   * 
   */

  public void initialize() {

    borrar = true;
    btChange.setText("Mostrar deshabilitados");
    cedula.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("cedula"));
    nombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombres"));
    apellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidos"));
    sede.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("sede"));
    rol.setCellValueFactory(new PropertyValueFactory<Empleado, String>("rol"));
    direccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
    telefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
    birthdate.setCellValueFactory(new PropertyValueFactory<Empleado, LocalDate>("birthdate"));
    mostrarTabla();
  }

  void mostrarTabla() {
    ObservableList<Empleado> s = FXCollections.observableArrayList();
    s.addAll(borrar ? Empleado.getEmpleadosHabilitados() : Empleado.getEmpleadosDeshabilitados());
    tableUsers.setItems(s);
  }

  @FXML
  void borrar(ActionEvent event) {
    System.out.println();
    Empleado e = tableUsers.getItems().get(tableUsers.getSelectionModel().getFocusedIndex());
    int op = JOptionPane.showConfirmDialog(null,
        "¿Está seguro que desea " + (borrar ? "borrar" : "habilitar") + " a " + e.getNombres() + "?");
    if (op == 0) {
      if (borrar)
        Usuario.deshabilitarUsuario(e.getCedula());
      else
        Usuario.habilitarUsuario(e.getCedula());
      mostrarTabla();
    }
  }

  @FXML
  void showOtherUsers(ActionEvent event) {
    borrar = !borrar;
    changeButtons();
    mostrarTabla();
  }

  private void changeButtons() {
    btBorrar.setText(borrar ? "Borrar" : "Habilitar");
    btChange.setText(borrar ? "Mostrar deshabilitados" : "Mostrar habilitados");
  }

  /**
   * Accede a la pestaña de registro de usuarios.
   * 
   * @param event not used.
   */
  @FXML
  void goToUsuariosRegistro(ActionEvent event) {
    View.newView("user.register", new UserRegister());
  }

  /**
   * Accede a la pestaña de edicion de empleado segúm el empleado seleccionado.
   * 
   * @param event not used.
   */
  @FXML
  void userEditButton(ActionEvent event) {
    Empleado e = tableUsers.getSelectionModel().getSelectedItem();
    if (e != null)
      Globals.cambiarVista("user.edit", new UserEdit(e));
    else
      SpecificAlerts.showUserNullAlert();
  }
}
