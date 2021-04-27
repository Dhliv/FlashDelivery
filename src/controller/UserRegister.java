package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import utilities.*;

public class UserRegister implements Initializable {
  private int NOEXISTE;
  private AnchorPane content;
  private Roles roles;
  private UserRegisterChecker userRegisterChecker;
  private int userNoExist;
  private UserRegisterAlert alerta;
  private Object controladorAnterior;

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
  private ChoiceBox<String> idsedeT;
  @FXML
  private PasswordField passwordT;

  /**
   * Constructor de la clase UserRegister
   * 
   * @param contenido   Contenedor de todos los componentes visuales de la actual
   *                    pestaña.
   * @param controlador Controller de la pestaña anterior.
   */
  /**
   * Constructor
   * 
   * @param contenido
   * @param controlador
   */
  public UserRegister(AnchorPane contenido, Object controlador) {
    content = contenido;
    controladorAnterior = controlador;
    alerta = new UserRegisterAlert();
    userRegisterChecker = new UserRegisterChecker();
    NOEXISTE = 1;
  }

  /**
   * Inicializador de algunos componentes gráficos.
   * 
   * @param url not used.
   * @param rb  not used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ObservableList<String> l = FXCollections.observableArrayList();
    ObservableList<String> s = FXCollections.observableArrayList();

    roles = new Roles();
    s.removeAll(s);
    l.removeAll(l);
    l.addAll(roles.rol);
    s.addAll(Globals.getSedes());
    rolT.getItems().addAll(l);
    idsedeT.getItems().addAll(s);
  }

  /**
   * Carga en 'content' la pantalla de consulta de usuarios.
   * 
   * @param event not used.
   */
  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    volver();
  }

  /**
   * Registra a un usuario.
   * 
   * @param event not used.
   */
  @FXML
  void registrarUser(ActionEvent event) {
    try {

      boolean forbidchar = false;
      boolean emptyCamps = false;
      // funcionGuardarCampos();
      String name = nombreT.getText();
      String telefono = telefonoT.getText();
      Object rl = rolT.getValue();
      String dir = direccionT.getText();
      String ident = identificacionT.getText();
      Object fecha = fechaT.getValue();
      Object idS = idsedeT.getValue();
      String username = usernameT.getText();
      String password = passwordT.getText();

      String campo[] = { name, telefono, dir, ident, username, password };
      Object multOpcion[] = { rl, fecha, idS };

      emptyCamps = userRegisterChecker.checkEmpty(campo, multOpcion); // verifica que no existan campos vacíos.
      forbidchar = userRegisterChecker.checkChar(campo); // verifica que no se hayan utilizado caracteres prohibidos.

      if (!forbidchar && !emptyCamps) { // Si no hay problemas con las validaciones hechas:
        // !funcionIntroducir();
        // #TODO Cambiar id a String en base de datos
        int id = Integer.valueOf(ident);
        LocalDate fc = LocalDate.parse(fecha.toString());
        int idSede = Globals.getIdSede(idS.toString());
        String rol = rl.toString();

        Empleado emp = new Empleado(id, name, "", rol, dir, telefono, fc, idSede);
        EmpleadoDAO empD = new EmpleadoDAO();
        userNoExist = empD.crearEmpleado(emp); // Almacena 1 si el empleado fue registrado con exito. 0 si el empleado
                                               // ya existía.

        if (userNoExist == NOEXISTE) {
          Usuario user = new Usuario(id, username, password, true);
          UsuarioDAO userD = new UsuarioDAO();
          userD.crearUsuario(user);
          alerta.showRegSuccess();
        } else {
          alerta.showUserExistAlert();
        }

        volver();
      } else { // Si hubo problemas en las validaciones, ejecuta la correspondiente alerta:
        if (emptyCamps)
          alerta.showEmptyFieldAlert();
        else if (forbidchar)
          alerta.showCharForbidenAlert();
      }
    } catch (NumberFormatException error) {
      alerta.showEmptyFieldAlert();
    }

  }

  /**
   * Retorna a la pantalla de consulta de usuarios.
   */
  private void volver() {
    content.getChildren().clear();
    Parent root = Globals.loadView("user.consulta", controladorAnterior);
    content.getChildren().add(root);
  }
}
