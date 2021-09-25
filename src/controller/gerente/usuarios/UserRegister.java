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

/**
 * La clase UserRegister se encarga de brindar al cliente la interfaz gráfica
 * para el resgistro de un empleado en la base de datos.
 * 
 * @author David Henao
 * @author Alejandro Pergueza Amaya
 * @version 1.0
 * @since 24/09/2021
 */
public class UserRegister {
  // private Roles roles; // Cargos de la empresa

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
   * Ingresa los datos a los menus desplegables de Roles y Sedes. Además establece
   * restricciones a los campos necesarios.
   * 
   */
  public void initialize() {
    ObservableList<String> rolesParaVista = FXCollections.observableArrayList();
    ObservableList<String> sedesParaVista = FXCollections.observableArrayList();

    sedesParaVista.removeAll(sedesParaVista);
    rolesParaVista.removeAll(rolesParaVista);
    rolesParaVista.addAll(Roles.roles);
    sedesParaVista.addAll(model.Entities.Sede.getSedesParsed());
    rolT.getItems().addAll(rolesParaVista);
    idsedeT.getItems().addAll(sedesParaVista);

    TextFieldRestrictions.textFieldNumeric(identificacionT);
    TextFieldRestrictions.textFieldMaxLength(identificacionT, 16);

  }

  /**
   * Obtiene los datos de los campos de registro y los almacena en variables
   * internas.
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
   * Convierte el rol visual a un rol interno.
   * 
   * @param rol String con rol visual.
   * @return String con rol interno.
   */
  private String parseRol(String rol) {
    if (rol == Roles.rol[Roles.SECRETARIO])
      return "Secretaria";
    return rol;
  }

  /**
   * Se transforman los tipos de datos que tienen tipo distinto a string.
   */
  private void parseData() {
    fc = LocalDate.parse(fecha);
    idSede = model.Entities.Sede.getIdSede(idS);
    rol = parseRol(rl.toString());
  }

  /**
   * Retorna a la pantalla de consulta de usuarios.
   */
  private void volver() {
    View.newView("user.consulta", new UserConsulta());
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
   * Registra a un usuario en la base de datos, haciendo las respectivas
   * validaciones (revisar que no existan campos vacíos, que no se usen caracteres
   * prohibidos, que el empleado a registrar no se encuentre registrado, que el
   * usuario asignado al empleado no se encuentre en uso, que la inserción de
   * datos en la BD sea exitosa).
   * 
   * @param event not used.
   */

  @FXML
  void registrarUser(ActionEvent event) {

    getData();
    // TODO Cambiar a manejo por objeto (Empleado y Usuario)
    String campo[] = { name, telefono, dir, ident, username, password, idS, rl, fecha, apellidos };

    boolean emptyCamps = GeneralChecker.checkEmpty(campo, new Object[0]);
    boolean forbidchar = GeneralChecker.checkChar(campo);
    boolean usernameExist = Usuario.checkExistence(username);
    boolean empleadoExist = Empleado.checkExistence(ident);

    if (forbidchar || emptyCamps || usernameExist || empleadoExist) {
      { // Si hubo problemas en las validaciones, ejecuta la correspondiente alerta:
        if (emptyCamps)
          SpecificAlerts.showEmptyFieldAlert();
        if (forbidchar)
          SpecificAlerts.showCharForbidenAlert();
        if (usernameExist)
          SpecificAlerts.showUserExist();
        if (empleadoExist)
          SpecificAlerts.showEmpleadoExists();
      }
    } else { // Si no hay problemas con las validaciones hechas:
      parseData();

      Usuario user = new Usuario(ident, username, password, true);
      Empleado emp = new Empleado(ident, name, apellidos, parseRol(rol), dir, telefono, fc, idSede);

      boolean registroFallido = (Empleado.crearEmpleado(emp) == 0) | !Usuario.registrarUsuario(user);
      if (registroFallido) // Si ocurrió algún error, se muestra eso en pantalla.
        SpecificAlerts.showErrorUnexpt();
      else {
        SpecificAlerts.showRegSuccess();
        volver();
      }
    }
  }
}
