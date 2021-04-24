package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
  private AnchorPane content;
  private Roles roles;
  private UserRegisterChecker userRegisterChecker;
  private int userNoExist;
  private UserRegisterAlert alerta;
  private Object controladorAnterior;
  private LoadView vista;

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
  private ChoiceBox<Integer> idsedeT;

  @FXML
  private PasswordField passwordT;

  /*
   * Constructor de la clase UserRegister.
   * 
   * Almacena en la clase el 'contenido' (ventana actual donde se hace el
   * registro) y 'controlador' lo almacena, para evitar crear uno nuevo con
   * parametros iniciales distintos. Adicionalmente se crea un objeto de la clase
   * Alerta con la que se invocarán a las alertas pertinentes a la creación de un
   * usuario, ademas de un objeto de la clase UserRegisterChecker para hacer las
   * validaciones pertinentes al registro de un usuario, y finalmente crea un
   * objeto de la clase LoadView para cargar "pestañas".
   */
  public UserRegister(AnchorPane contenido, Object controlador) {
    content = contenido;
    controladorAnterior = controlador;
    alerta = new UserRegisterAlert();
    userRegisterChecker = new UserRegisterChecker();
    vista = new LoadView();
  }

  /*
   * Inicializa las elecciones disponibles en las ChoiceBox.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ObservableList<String> l = FXCollections.observableArrayList();
    ObservableList<Integer> s = FXCollections.observableArrayList();
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

  /*
   * Carga en 'content' la pantalla de consulta de usuarios.
   */
  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    volver();
  }

  /*
   * Registra a un usuario.
   * 
   * Se obtienen los datos registrados en cada campo habilitado, y se hacen las
   * respectivas verificaciones: no pueden haber campos vacíos, no puede haber
   * caracteres prohibidos, y un usuario no puede registrarse dos veces.
   * 
   * En caso de obtener una verificación aceptada, se le indica al usuario que su
   * registro fue exitoso, de lo contrario se desplegará en pantalla un pop-up
   * donde indique la clase de 'error' en la que incurrió.
   */
  @FXML
  void registrarUser(ActionEvent event) {
    try {

      boolean forbidchar = false;
      boolean emptyCamps = false;

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

      forbidchar = userRegisterChecker.checkChar(campo);
      emptyCamps = userRegisterChecker.checkEmpty(campo, multOpcion);

      if (!forbidchar && !emptyCamps) {
        // funcionIntroducir();
        // #TODO Cambiar a String en base de datos
        int id = Integer.valueOf(ident);
        LocalDate fc = LocalDate.parse(fecha.toString());
        int idSede = Integer.valueOf(idS.toString());
        String rol = rl.toString();

        Empleado emp = new Empleado(id, name, "", rol, dir, telefono, fc, idSede);
        EmpleadoDAO empD = new EmpleadoDAO();
        userNoExist = empD.crearEmpleado(emp);

        if (userNoExist == 1) {
          Usuario user = new Usuario(id, username, password, true);
          UsuarioDAO userD = new UsuarioDAO();
          userD.crearUsuario(user);
        } else {
          alerta.showUserExistAlert();
        }

        alerta.showRegSuccess();
        volver();
      } else {
        if (emptyCamps)
          alerta.showEmptyFieldAlert();
        else if (forbidchar)
          alerta.showCharForbidenAlert();
      }
    } catch (NumberFormatException error) {
      alerta.showEmptyFieldAlert();
    }

  }

  /*
   * Retorna a la pantalla de consulta de usuarios.
   */
  void volver() {
    content.getChildren().clear();
    Parent root = vista.loadView("user.consulta", controladorAnterior);
    content.getChildren().add(root);
  }
}
