package controller.gerente.usuarios;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Entities.Empleado;
import utilities.*;

/**
 * Clase controller UserEdit. Contiene la parte visual de la edición de un
 * usuario.
 * 
 * @author David Henao
 * @version 1.0
 * @since 25/09/2021
 */
public class UserEdit implements Initializable {
  private Empleado aEditar;

  // Auxiliares para los datos del usuario.
  private String fc; // Dato parcial de fecha de nacimiento
  private String idS; // Dato parcial de id sede

  // Variables que contienen los datos del usuario.
  private String name; // Nombre
  private String apellidos; // Apellidos
  private String telefono; // Telefono
  private String dir; // Dirección
  private String ident; // Identificación
  private String rol; // Rol
  private LocalDate fecha; // Fecha de nacimiento
  private int idSede; // Id de la Sede

  // Campos de texto que se pueden rellenar en user.register view
  @FXML
  private JFXTextField nombreT;
  @FXML
  private JFXTextField apellidoT;
  @FXML
  private JFXTextField identificacionT;
  @FXML
  private JFXTextField telefonoT;
  @FXML
  private JFXTextField direccionT;
  @FXML
  private DatePicker fechaT;
  @FXML
  private ChoiceBox<String> rolT;
  @FXML
  private ChoiceBox<String> idsedeT;
  @FXML
  private Label lblEmpleadoEditar;
  // FIN de los campos.

  /**
   * Constructor del controlador UserEdit.
   * 
   * @param e   empleado que se va a editar.
   * @param ctr controlador de la vista anterior.
   */
  public UserEdit(Empleado e) {
    aEditar = e;
  }

  /**
   * Inicializador de algunos componentes gráficos. Además, se agregan
   * restricciones a algunos campos de texto.
   * 
   * @param url not used.
   * @param rb  not used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ObservableList<String> roles = FXCollections.observableArrayList();
    ObservableList<String> sedes = FXCollections.observableArrayList();

    sedes.removeAll(sedes);
    roles.removeAll(roles);
    roles.addAll(Roles.roles);
    sedes.addAll(model.Entities.Sede.getSedesParsed());
    rolT.getItems().addAll(roles);
    idsedeT.getItems().addAll(sedes);

    nombreT.setText(aEditar.getNombres());
    apellidoT.setText(aEditar.getApellidos());
    identificacionT.setText(aEditar.getCedula());
    telefonoT.setText(aEditar.getTelefono());
    direccionT.setText(aEditar.getDireccion());
    fechaT.setValue(aEditar.getBirthdate());
    rolT.setValue(aEditar.getRol());
    idsedeT.setValue(model.Entities.Sede.parseSede(aEditar.getSede()));

    lblEmpleadoEditar.setText(lblEmpleadoEditar.getText() + " " + aEditar.getNombres());
    identificacionT.setEditable(false);

    TextFieldRestrictions.textFieldMaxLength(telefonoT, 10);
    TextFieldRestrictions.textFieldMaxLength(identificacionT, 10);
  }

  /**
   * Verifica si la cédula y el teléfono cumplen con el formato numérico.
   * 
   * @return True si se cumple el formato numérico, false de lo contrario.
   */
  private Boolean checkFormat() {
    String[] campos = { telefono, ident };
    return TextFieldRestrictions.checkNumericExpression(campos);
  }

  /**
   * Obtiene los datos de los campos de registro.
   */
  private void getData() {
    name = nombreT.getText();
    apellidos = apellidoT.getText();
    telefono = telefonoT.getText();
    dir = direccionT.getText();
    ident = identificacionT.getText();

    fc = fechaT.getValue() == null ? "" : fechaT.getValue().toString();
    idS = idsedeT.getValue() == null ? "" : idsedeT.getValue().toString();
    rol = rolT.getValue() == null ? "" : rolT.getValue().toString();
  }

  /**
   * Se transforman los tipos de datos que tienen tipo distinto a string.
   */
  private void parseData() {
    fecha = LocalDate.parse(fc);
    idSede = model.Entities.Sede.getIdSede(idS);
  }

  /**
   * Retorna a la pantalla de consulta de empleados.
   */
  private void volver() {
    View.newView("user.consulta", new UserConsulta());
  }

  /**
   * Vuelve al apartado de consulta de Empleados.
   * 
   * @param event not used.
   */
  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    volver();
  }

  /**
   * Accede a la pantalla de registrar un usuario nuevo.
   * 
   * @param event not used.
   */
  @FXML
  void goToUsuariosRegistro(ActionEvent event) {
    View.newView("user.register", new UserRegister());
  }

  /**
   * Actualiza los datos de un usuario en la base de datos, haciendo las
   * validaciones necesarias.
   * 
   * @param event not used.
   */
  @FXML
  void updateEmpleado(ActionEvent event) {
    getData();

    String campos[] = { name, telefono, dir, ident, fc, idS, rol };

    Boolean camposVacios = GeneralChecker.checkEmpty(campos, new Object[0]);
    Boolean forbidChar = GeneralChecker.checkChar(campos);
    Boolean formatoCorrecto = checkFormat();

    if (!(camposVacios || forbidChar || !formatoCorrecto)) {
      parseData();
      Empleado updated = new Empleado(ident, name, apellidos, rol, dir, telefono, fecha, idSede);
      Empleado.updateEmpleado(updated);
      SpecificAlerts.showUpdSucces();
      volver();
    } else {
      if (camposVacios)
        SpecificAlerts.showEmptyFieldAlert();
      if (forbidChar)
        SpecificAlerts.showCharForbidenAlert();
      if (!formatoCorrecto)
        SpecificAlerts.showNumericFormat();
    }
  }

}
