package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.Empleado;
import model.EmpleadoDAO;
import model.Usuario;
import model.UsuarioDAO;
import utilities.*;

public class UserRegister implements Initializable {
  private AnchorPane content;
  private Object contAnterior;
  private Roles roles;
  private Alert cVacios;

  public UserRegister() {
    Alert a = new Alert(AlertType.NONE);
    a.setAlertType(AlertType.WARNING);
    a.setContentText("Ningún campo debe estar vacío");
    a.setTitle("Campos Vacíos");
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
  private ChoiceBox<String> rolT;

  @FXML
  private TextField usernameT;

  @FXML
  private ChoiceBox<?> idsedeT;

  @FXML
  private PasswordField passwordT;

  public UserRegister(AnchorPane contenido, Object controlador) {
    content = contenido;
    contAnterior = controlador;
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ObservableList l = FXCollections.observableArrayList();
    ObservableList s = FXCollections.observableArrayList();
    ArrayList<Integer> sedes = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      sedes.add(i);
    }

    roles = new Roles();
    s.removeAll(s);
    l.removeAll(l);
    l.addAll(roles.rol);
    s.addAll(sedes);
    rolT.getItems().addAll(l);
    idsedeT.getItems().addAll(s);
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    volver();
  }

  @FXML
  void registrarUser(ActionEvent event) {
    try {
      boolean empty = false;
      String name = nombreT.getText();
      String telefono = telefonoT.getText();
      String rol = rolT.getValue();
      String dir = direccionT.getText();
      String ident = identificacionT.getText();
      var fecha = fechaT.getValue();
      var idS = idsedeT.getValue();
      String username = usernameT.getText();
      String password = passwordT.getText();
      Object campo[] = { name, telefono, rol, dir, ident, fecha, idS, username, password };
      for (int i = 0; i < 9; i++) {
        if (campo[i] == null || campo[i].equals("")) {
          empty = true;
          break;
        }
      }

      if (!empty) {
        // #TODO Cambiar a String en base de datos
        int id = Integer.valueOf(ident);
        LocalDate fc = LocalDate.parse(fecha.toString());
        int idSede = Integer.valueOf(idS.toString());

        Empleado emp = new Empleado(id, name, "", rol, dir, telefono, fc, idSede);
        EmpleadoDAO empD = new EmpleadoDAO();
        empD.crearEmpleado(emp);
        Usuario user = new Usuario(id, username, password, true);
        UsuarioDAO userD = new UsuarioDAO();
        userD.crearUsuario(user);

        volver();
      } else
        cVacios.show();
    } catch (NumberFormatException error) {
      cVacios.show();
    }

  }

  void volver() {
    content.getChildren().clear();
    var loader = new FXMLLoader(getClass().getResource("../view/user.consulta.fxml"));
    loader.setController(contAnterior);
    Parent root;
    try {
      root = loader.load();
      content.getChildren().add(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
