package controller;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class UserRegister implements Initializable {
  private AnchorPane content;
  private Object contAnterior;

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

    s.removeAll(s);
    l.removeAll(l);
    l.addAll("cosa1", "cosa2", "cosa3");
    s.addAll(sedes);
    rolT.getItems().addAll(l);
    idsedeT.getItems().addAll(s);
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
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

    try {
      String name = nombreT.getText();
      int id = Integer.valueOf(identificacionT.getText());
      String telefono = telefonoT.getText();
      String rol = rolT.getValue().toString();
      String dir = direccionT.getText();
      LocalDate fc = LocalDate.parse(fechaT.getValue().toString());
      int idSede = Integer.valueOf(idsedeT.getValue().toString());
      String username = usernameT.getText();
      String password = passwordT.getText();

      Empleado emp = new Empleado(id, name, "", rol, dir, telefono, fc, idSede);
      EmpleadoDAO empD = new EmpleadoDAO();
      empD.crearEmpleado(emp);

      Usuario user = new Usuario(id, username, password, true);
      UsuarioDAO userD = new UsuarioDAO();
      userD.crearUsuario(user);
    } catch (NumberFormatException error) {
      System.out.println("MORÍ");
    }
  }

}
