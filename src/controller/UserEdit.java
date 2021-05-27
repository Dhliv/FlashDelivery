package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Empleado;
import utilities.*;

public class UserEdit implements Initializable {
  private Roles roles; // Cargos de la empresa
  private UserConsulta controladorAnterior;
  private Empleado aEditar;

  // Auxiliares para los datos del usuario.
  private Object fc; // Dato parcial de fecha de nacimiento
  private Object idS; // Dato parcial de id sede
  private Object rl; // Dato parcial de rol
  private Boolean camposVacios;
  private Boolean forbidChar;

  // Variables que contienen los datos del usuario.
  private String name; // Nombre
  private String telefono;
  private String dir; // Dirección
  private String ident; // Identificación
  private String rol;
  private LocalDate fecha; // Fecha de nacimiento
  private int idSede;

  // Campos de texto que se pueden rellenar en user.register view
  @FXML private TextField nombreT;
  @FXML private TextField identificacionT;
  @FXML private TextField telefonoT;
  @FXML private TextField direccionT;
  @FXML private DatePicker fechaT;
  @FXML private ChoiceBox<String> rolT;
  @FXML private ChoiceBox<String> idsedeT;
  @FXML private Label lblEmpleadoEditar;
  // FIN de los campos.

  /**
   * Constructor de la clase UserRegister
   * 
   * @param contenido   Contenedor de todos los componentes visuales de la actual
   *                    pestaña.
   * @param controlador Controller de la pestaña anterior.
   */
  public UserEdit(Empleado e, UserConsulta ctr) {
    aEditar = e;
    controladorAnterior = ctr;
  }

  /**
   * Inicializador de algunos componentes gráficos.
   * 
   * @param url not used.
   * @param rb  not used.
   */
  @Override public void initialize(URL url, ResourceBundle rb) {
    ObservableList<String> l = FXCollections.observableArrayList();
    ObservableList<String> s = FXCollections.observableArrayList();

    roles = new Roles();
    s.removeAll(s);
    l.removeAll(l);
    l.addAll(roles.rol);
    s.addAll(model.Sedes.getSedesParsed());
    rolT.getItems().addAll(l);
    idsedeT.getItems().addAll(s);

    nombreT.setText(aEditar.getNombres());
    identificacionT.setText(aEditar.getCedula());
    telefonoT.setText(aEditar.getTelefono());
    direccionT.setText(aEditar.getDireccion());
    fechaT.setValue(aEditar.getBirthdate());
    rolT.setValue(aEditar.getRol());
    idsedeT.setValue(model.Sedes.parseSede(aEditar.getSede()));

    lblEmpleadoEditar.setText(lblEmpleadoEditar.getText() + " " + aEditar.getNombres());
    identificacionT.setEditable(false);
  }

  /**
   * Obtiene los datos de los campos de registro.
   */
  private void getData() {
    name = nombreT.getText();
    telefono = telefonoT.getText();
    dir = direccionT.getText();
    ident = identificacionT.getText();

    fc = fechaT.getValue();
    idS = idsedeT.getValue();
    rl = rolT.getValue();
  }

  /**
   * Se transforman los tipos de datos que tienen tipo distinto a string.
   */
  private void parseData() {
    fecha = LocalDate.parse(fc.toString());
    idSede = model.Sedes.getIdSede(idS.toString());
    rol = rl.toString();
  }

  /**
   * Retorna a la pantalla de consulta de empleados.
   */
  private void volver() {
    Globals.cambiarVista(Globals.loadView("user.consulta", controladorAnterior));
  }

  /**
   * Vuelve al apartado de consulta de Empleados.
   * 
   * @param event not used.
   */
  @FXML void goToUsuariosConsulta(ActionEvent event) {
    volver();
  }

  /**
   * Accede a la pantalla de registrar un usuario nuevo.
   * 
   * @param event not used.
   */
  @FXML void goToUsuariosRegistro(ActionEvent event) {
    Globals.cambiarVista(Globals.loadView("user.register", new UserRegister(controladorAnterior)));
  }

  /**
   * Actualiza los datos de un usuario en la base de datos.
   * 
   * @param event not used.
   */
  @FXML void updateEmpleado(ActionEvent event) {
    getData();

    String campos[] = { name, telefono, dir, ident };
    Object objetos[] = { fc, idS, rl };

    camposVacios = GeneralChecker.checkEmpty(campos, objetos);
    forbidChar = GeneralChecker.checkChar(campos);

    if (!(camposVacios || forbidChar)) {
      parseData();
      Empleado updated = new Empleado(ident, name, "", rol, dir, telefono, fecha, idSede);
      Empleado.updateEmpleado(updated);
      GeneralAlerts.showUpdSucces();
      volver();
    } else {
      if (camposVacios)
        GeneralAlerts.showEmptyFieldAlert();
      else
        GeneralAlerts.showCharForbidenAlert();
    }
  }

}
