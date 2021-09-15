package controller.gerente.usuarios;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Entities.Empleado;
import model.Entities.Usuario;
import utilities.*;

public class UserRegister {
  private static final int NOEXISTE = 1; // Usuario no se encuentra en la BD
  private static final int EXISTE = 0; // Usuario se encuentra en la BD
  private Roles roles; // Cargos de la empresa

  // Auxiliares para los datos del usuario.
  private String fecha; // Dato parcial de fecha de nacimiento
  private String idS; // Dato parcial de id sede
  private String rl; // Dato parcial de rol

  // Variables que contienen los datos del usuario.
  private String name; // Nombre
  private String apellidos; // Apellidos.
  private String telefono;
  private String dir; // Dirección
  private String ident; // Identificación
  private String username;
  private String password;
  private String rol;
  private LocalDate fc; // Fecha de nacimiento
  private int id;
  private int idSede;

  // Campos de texto que se pueden rellenar en user.register view
  @FXML
  private TextField nombreT;
  @FXML
  private TextField apellidoT;
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
  // FIN de los campos.

  /**
   * Constructor de la clase UserRegister
   * 
   */
  public UserRegister() {

  }

  /**
   * Ingresa los datos a los menus desplegables de Roles y Sedes. Además establece
   * restricciones a los campos necesarios.
   * 
   */
  public void initialize() {
    ObservableList<String> l = FXCollections.observableArrayList();
    ObservableList<String> s = FXCollections.observableArrayList();

    roles = new Roles();
    s.removeAll(s);
    l.removeAll(l);
    l.addAll(roles.rol);
    s.addAll(model.Entities.Sede.getSedesParsed());
    rolT.getItems().addAll(l);
    idsedeT.getItems().addAll(s);

    TextFieldRestrictions.textFieldNumeric(identificacionT);
    TextFieldRestrictions.textFieldMaxLength(identificacionT, 16);

    TextFieldRestrictions.textFieldNumeric(telefonoT);
    TextFieldRestrictions.textFieldMaxLength(identificacionT, 16);
  }

  /**
   * Obtiene los datos de los campos de registro y los almacena en las variables
   * de la linea 38.
   */
  private void getData() {
    name = nombreT.getText();
    apellidos = apellidoT.getText();
    telefono = telefonoT.getText();
    rl = rolT.getValue();
    dir = direccionT.getText();
    ident = identificacionT.getText();
    fecha = fechaT.getValue() != null ? fechaT.getValue().toString() : "";
    idS = idsedeT.getValue();
    username = usernameT.getText();
    password = passwordT.getText();
  }

  /**
   * Limpia todos los campos rellenables.
   */
  private void clearCamps() {
    nombreT.setText("");
    apellidoT.setText("");
    telefonoT.setText("");
    direccionT.setText("");
    identificacionT.setText("");
    usernameT.setText("");
    passwordT.setText("");

    fechaT.setValue(null);
    idsedeT.setValue(null);
    rolT.setValue(null);
  }

  /**
   * Convierte el rol visual a un rol interno.
   * 
   * @param rol String con rol visual.
   * @return String con rol interno.
   */
  private String parseRol(String rol) {
    if (rol == roles.rol.get(roles.SECRETARIO))
      return "Secretaria";
    return rol;
  }

  /**
   * Se transforman los tipos de datos que tienen tipo distinto a string.
   */
  private void parseData() {
    id = Integer.valueOf(ident);
    fc = LocalDate.parse(fecha);
    idSede = model.Entities.Sede.getIdSede(idS);
    rol = parseRol(rl.toString());
  }

  /**
   * Retorna a la pantalla de consulta de usuarios.
   */
  private void volver() {
    Globals.cambiarVista("user.consulta", new UserConsulta());
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
      boolean usernameExist = false;
      boolean empleadoExist = false;
      boolean registroFallido = false;

      getData();
      String campo[] = { name, telefono, dir, ident, username, password, idS, rl, fecha, idS, apellidos };
      emptyCamps = GeneralChecker.checkEmpty(campo, new Object[0]); // verifica que no existan campos vacíos.
      forbidchar = GeneralChecker.checkChar(campo); // verifica que no se hayan utilizado caracteres prohibidos.

      if (!forbidchar && !emptyCamps) { // Si no hay problemas con las validaciones hechas:
        parseData();
        usernameExist = Usuario.checkExistence(username);
        empleadoExist = Empleado.checkExistence(id + "");

        if (!(usernameExist || empleadoExist)) { // Si el empleado y el usuario no están registrados, se procede a hacer el registro.
          Usuario user = new Usuario(id, username, password, true);
          Empleado emp = new Empleado(id + "", name, apellidos, parseRol(rol), dir, telefono, fc, idSede);
          registroFallido = (Empleado.crearEmpleado(emp) != 0);
          registroFallido |= Usuario.registrarUsuario(user);

          if(registroFallido) // Si ocurrió algún error, se muestra eso en pantalla.
            SpecificAlerts.showErrorUnexpt();
          else{
            SpecificAlerts.showRegSuccess();
            volver();
            clearCamps();
          }
        } else {
          if(usernameExist) SpecificAlerts.showUsernameExist();
          if(empleadoExist) SpecificAlerts.showEmpleadoExists();
        }
      } else { // Si hubo problemas en las validaciones, ejecuta la correspondiente alerta:
        if (emptyCamps)
          SpecificAlerts.showEmptyFieldAlert();
        else if (forbidchar)
          SpecificAlerts.showCharForbidenAlert();
      }
    } catch (NumberFormatException error) {
      SpecificAlerts.showErrorUnexpt();
    }
  }

}
