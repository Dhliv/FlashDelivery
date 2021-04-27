package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Usuario;
import utilities.*;

public class UserConsulta implements Initializable {

  private AnchorPane content;
  private Parent userRegister;
  private UserConsultaButtons botones;

  @FXML
  private TableView<Usuario> tableUsers;
  @FXML
  private TableColumn<Usuario, Integer> cedula;
  @FXML
  private TableColumn<Usuario, String> nombre;
  @FXML
  private TableColumn<Usuario, String> apellido;
  @FXML
  private TableColumn<Usuario, Boolean> sede;

  public UserConsulta(AnchorPane cont) {
    content = cont;
    botones = new UserConsultaButtons();
  }

  /**
   * Inicializa los datos de la tabla de empleados.
   * 
   * @param location  not used.
   * @param resources not used.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cedula.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("ID"));
    nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
    apellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("password"));
    sede.setCellValueFactory(new PropertyValueFactory<Usuario, Boolean>("enabled"));
    ObservableList<Usuario> s = FXCollections.observableArrayList();

    s.add(new Usuario(1234, "APA", "APA", true));
    s.add(new Usuario(1235, "PAPU", "PAPU", true));
    // insertData();

    tableUsers.setItems(s);
  }

  @FXML
  void borrar(ActionEvent event) {

  }

  /**
   * Accede a la pestaña de registro de usuarios.
   * 
   * @param event not used.
   */
  @FXML
  void goToUsuariosRegistro(ActionEvent event) {
    content.getChildren().clear();
    userRegister = Globals.loadView("user.register", new UserRegister(content, this));
    content.getChildren().addAll(userRegister);
  }

  /**
   * Accede a la pestaña de edicion de empleado segúm el empleado seleccionado.
   * 
   * @param event not used.
   */
  @FXML
  void userEditButton(ActionEvent event) {
    Usuario usuario = tableUsers.getSelectionModel().getSelectedItem();
    botones.goToUserEdit(usuario, content);
  }
}
