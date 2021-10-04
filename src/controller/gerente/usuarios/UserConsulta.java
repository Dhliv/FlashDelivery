package controller.gerente.usuarios;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Empleado;
import model.Entities.Usuario;
import utilities.*;

/**
 * Clase controller UserConsulta. Contiene componentes y métodos gráficos
 * relacionados a la consulta de usuarios.
 * 
 * @author David Henao
 * @author Reynell Arkad Devji Quevedo
 * @version 1.0
 * @since 25/09/2021
 */
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

  /**
   * Carga la información en la tabla, mostrando a los empleados
   * habilitados/deshabilitados según sea el caso.
   */
  void mostrarTabla() {
    ObservableList<Empleado> s = FXCollections.observableArrayList();
    s.addAll(borrar ? Empleado.getEmpleadosHabilitados() : Empleado.getEmpleadosDeshabilitados());
    tableUsers.setItems(s);
  }

  /**
   * Deshabilita/habilita, según sea el caso, al empleado seleccionado en la tabla
   * de empleados.
   * 
   * @param event not used.
   */
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

  /**
   * Cambia la información desplegada en la tabla, mostrando empleados
   * deshabilitados/habilitados. Adicionalmenre también cambia el estado actual de
   * 'borrar', que indica si un usuario se va a habilitar o deshabilitar.
   * 
   * @param event not used.
   */
  @FXML
  void showOtherUsers(ActionEvent event) {
    borrar = !borrar;
    changeButtons();
    mostrarTabla();
  }

  /**
   * Cambia los valores visuales de los botones que implican borrar/habilitar a un
   * usuario, o desplegar a los empleados habilitados/deshabilitados.
   */
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
   * Accede a la pestaña de edicion de empleado según el empleado seleccionado. En
   * caso de no haberse seleccionado un empleado para su edición, se mostrará la
   * respectuva alerta indicando esa situación.
   * 
   * @param event not used.
   */
  @FXML
  void userEditButton(ActionEvent event) {
    Empleado e = tableUsers.getSelectionModel().getSelectedItem();
    if (e != null)
      View.cambiar("user.edit", new UserEdit(e));
    else
      SpecificAlerts.showUserNullAlert();
  }
}
