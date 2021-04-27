package controller;

import java.net.URL;
import java.time.LocalDate;
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
  private static final int NOEXISTE = 1; //Usuario no se encuentra en la BD
  private AnchorPane content; //Componente grafico padre
  private Roles roles; //Cargos de la empresa
  private UserRegisterChecker userRegisterChecker; //Clase auxiliar para comprobar validez
  private int userNoExist;
  private UserRegisterAlert alerta; //Objeto encargado de imprimir alertas
  private Object controladorAnterior;

  //Auxiliares para los datos del usuario.
  private Object fecha; //Dato parcial de fecha de nacimiento
  private Object idS; //Dato parcial de id sede
  private Object rl; //Dato parcial de rol

  //Variables que contienen los datos del usuario.
  private String name; //Nombre
  private String telefono;
  private String dir; //Dirección
  private String ident; //Identificación
  private String username;
  private String password;
  private String rol;
  private LocalDate fc; //Fecha de nacimiento
  private int id;
  private int idSede;


  //Campos de texto que se pueden rellenar en user.register view
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
  //FIN de los campos.

  /**
   * Constructor de la clase UserRegister
   * 
   * @param contenido   Contenedor de todos los componentes visuales de la actual
   *                    pestaña.
   * @param controlador Controller de la pestaña anterior.
   */
  public UserRegister(AnchorPane contenido, Object controlador) {
    content = contenido;
    controladorAnterior = controlador;
    alerta = new UserRegisterAlert();
    userRegisterChecker = new UserRegisterChecker();
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
   * Obtiene los datos de los campos de registro y los almacena en las variables de la linea 38.
   */
  private void getData() {
    name = nombreT.getText();
    telefono = telefonoT.getText();
    rl = rolT.getValue();
    dir = direccionT.getText();
    ident = identificacionT.getText();
    fecha = fechaT.getValue();
    idS = idsedeT.getValue();
    username = usernameT.getText();
    password = passwordT.getText();
  }

  /**
   * Se transforman los tipos de datos que tienen tipo distinto a string.
   */
  private void parseData() {
    id = Integer.valueOf(ident);
    fc = LocalDate.parse(fecha.toString());
    idSede = Globals.getIdSede(idS.toString());
    rol = rl.toString();
  }

  /**
   * Retorna a la pantalla de consulta de usuarios.
   */
  private void volver() {
    content.getChildren().clear();
    Parent root = Globals.loadView("user.consulta", controladorAnterior);
    content.getChildren().add(root);
  }

  // #---------------------------------------------------------------------------
    // # FXML: ARCHIVOS DE JAVA FXML
    // #---------------------------------------------------------------------------

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

      getData();
      String campo[] = { name, telefono, dir, ident, username, password };
      Object multOpcion[] = { rl, fecha, idS };

      emptyCamps = userRegisterChecker.checkEmpty(campo, multOpcion); // verifica que no existan campos vacíos.
      forbidchar = userRegisterChecker.checkChar(campo); // verifica que no se hayan utilizado caracteres prohibidos.

      if (!forbidchar && !emptyCamps) { // Si no hay problemas con las validaciones hechas:
        parseData();

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

}
