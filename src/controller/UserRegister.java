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
  private Alert charForbi;
  private Alert userExist;
  private Alert regExitoso;
  private boolean emptyCamps;
  private boolean forbidchar;
  private int userNoExist;

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

    cVacios = new Alert(AlertType.NONE);
    cVacios.setAlertType(AlertType.WARNING);
    cVacios.setContentText("Por favor rellene los campos restantes.");
    cVacios.setTitle("Campos Vacíos");
    cVacios.setHeaderText("Existen campos vacíos");

    charForbi = new Alert(AlertType.NONE);
    charForbi.setAlertType(AlertType.WARNING);
    charForbi.setContentText("No es posible utilizar los siguientes caracteres: . , \' \" * = + - _ !");
    charForbi.setTitle("Caracteres Prohibidos");
    charForbi.setHeaderText("Se detectó el uso de caracteres prohibidos");

    userExist = new Alert(AlertType.NONE);
    userExist.setAlertType(AlertType.WARNING);
    userExist.setContentText("Por favor rellene los campos para un nuevo empleado.");
    userExist.setTitle("Empleado Repetido");
    userExist.setHeaderText("Ya existe este empleado");

    regExitoso = new Alert(AlertType.NONE);
    regExitoso.setAlertType(AlertType.INFORMATION);
    regExitoso.setContentText("El empleado se ha registrado con exito.");
    regExitoso.setTitle("Registro Exitoso");
    regExitoso.setHeaderText("");
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
      boolean check = false;
      String name = nombreT.getText();
      String telefono = telefonoT.getText();
      String rol = rolT.getValue();
      String dir = direccionT.getText();
      String ident = identificacionT.getText();
      Object fecha = fechaT.getValue();
      Object idS = idsedeT.getValue();
      String username = usernameT.getText();
      String password = passwordT.getText();
      String campo[] = { name, telefono, rol, dir, ident, username, password };

      check = checkEmpty(campo, fecha, idS);
      check = checkChar(campo);

      if (!check) {
        // #TODO Cambiar a String en base de datos
        int id = Integer.valueOf(ident);
        LocalDate fc = LocalDate.parse(fecha.toString());
        int idSede = Integer.valueOf(idS.toString());

        Empleado emp = new Empleado(id, name, "", rol, dir, telefono, fc, idSede);
        EmpleadoDAO empD = new EmpleadoDAO();
        userNoExist = empD.crearEmpleado(emp);

        if (userNoExist == 1) {
          Usuario user = new Usuario(id, username, password, true);
          UsuarioDAO userD = new UsuarioDAO();
          userD.crearUsuario(user);
        } else {
          userExist.show();
        }

        regExitoso.show();
        volver();
      } else {
        if (emptyCamps)
          cVacios.show();
        else if (forbidchar)
          charForbi.show();
      }
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

  boolean checkChar(String campo[]) {
    boolean ch = false;
    forbidchar = false;
    char F[] = { '.', ',', '\'', '\"', '*', '=', '+', '-', '_', '!' };

    for (int i = 0; i < campo.length; i++) {
      for (int j = 0; j < campo[i].length(); j++) {
        for (int k = 0; k < F.length; k++) {
          if (campo[i].charAt(j) == F[k]) {
            ch = true;
            forbidchar = true;
          }
        }
      }
    }

    return ch;
  }

  boolean checkEmpty(String campo[], Object fecha, Object idSede) {
    boolean ch = false;
    emptyCamps = false;

    for (int i = 0; i < campo.length; i++) {
      if (campo[i] == null || campo[i].equals("")) {
        ch = true;
        emptyCamps = true;
        if (campo[i] == null)
          campo[i] = "";
      }
    }

    if (fecha == null || idSede == null)
      ch = true;

    return ch;
  }

}
