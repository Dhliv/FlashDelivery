package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Empleado;
import model.EmpleadoDAO;
import model.Usuario;
import model.UsuarioDAO;

public class UserRegister {
  private AnchorPane content;

  public UserRegister() {

  }

  @FXML
  private TextField nombreT;

  @FXML
  private TextField identificacionT;

  @FXML
  private TextField telefonoT;

  @FXML
  private TextField direccionT;

  @FXML
  private DatePicker fechaT;

  @FXML
  private ChoiceBox<?> rolT;

  @FXML
  private TextField usernameT;

  @FXML
  private ChoiceBox<?> idsedeT;

  @FXML
  private PasswordField passwordT;

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    content.getChildren().clear();
    var loader = new FXMLLoader(getClass().getResource("../view/user.consulta.fxml"));
    loader.setController(this);
    Parent root;
    try {
      root = loader.load();
      content.getChildren().add(root);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String name = nombreT.getText();
    int id = Integer.valueOf(identificacionT.getText());
    String telefono = telefonoT.getText();
    // String rol = rolT.getValue();
    String dir = direccionT.getText();
    // String fc = fechaT.getValue();
    // int idSede = idsedeT.getValue();
    String username = usernameT.getText();
    String password = passwordT.getText();

    Empleado emp = new Empleado(id, name, "", "Gerente", direccion, telefono, fc, idSede);
    EmpleadoDAO empD = new EmpleadoDAO();
    empD.crearEmpleado(emp);

    Usuario user = new Usuario(id, username, password, true);
    UsuarioDAO userD = new UsuarioDAO();
    userD.crearUsuario(user);
  }

}
