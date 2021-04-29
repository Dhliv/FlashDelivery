package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Clientes;
import utilities.GeneralAlerts;
import utilities.GeneralChecker;
import utilities.Globals;
import utilities.Ventana;

public class ClienteRegister implements Initializable {

  private String cedula; // cedula del cliente
  private String nombre; // nombre del cliente
  private String direccion; // direccion del cliente
  private String telefono; // telefono del cliente
  private String[] textos; // conjunto de strings de los datos del cliente
  private Object[] objetos; // conjunto vacío de objetos para validaciones
  private int iteracion; // indica si estoy resgistrando al remitente o destinatario
  private Ventana ventana; // objeto para abrir la siguiente ventana
  private static final int REMITENTE = 0; // distingue la iteracion
  private OperadorRegister operadorRegister; // controlador para registrar el envio de un paquete

  @FXML
  private TextField cedulaT;
  @FXML
  private TextField nombreT;
  @FXML
  private TextField direccionT;
  @FXML
  private TextField telefonoT;

  /**
   * Constructor de la clase ClienteRegister.
   * 
   * @param ced        cedula del cliente que se verificó en la pestaña
   *                   ClienteCheck.
   * @param it         iteracion actual: remitente o destinatario.
   * @param controller controlador de la siguiente ventana(registro de envío de
   *                   paquete).
   */
  public ClienteRegister(String ced, int it, OperadorRegister controller) {
    cedula = ced;
    iteracion = it;
    operadorRegister = controller;
  }

  /**
   * Inicializa el textField 'cedulaT' con la cedula obtenida por el constructor.
   * 
   * @param location  not used.
   * @param resources not used.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cedulaT.setText(cedula);
  }

  /**
   * Se registra a un Cliente en la base de datos.
   * 
   * @param event not used.
   */
  @FXML
  void registrarCliente(ActionEvent event) {
    getData();

    boolean forbidchar = GeneralChecker.checkChar(textos);
    boolean emptyCamps = GeneralChecker.checkEmpty(textos, objetos);

    if (!(forbidchar || emptyCamps)) {
      Clientes.createCliente(cedula, nombre, direccion, telefono);
      volver();
      GeneralAlerts.showRegSuccess();
    } else {
      if (emptyCamps)
        GeneralAlerts.showEmptyFieldAlert();
      else
        GeneralAlerts.showCharForbidenAlert();
    }
  }

  /**
   * Se obtienen los datos ingresados en los textFields y se colocan en arrays
   * para su validación.
   */
  private void getData() {
    nombre = nombreT.getText();
    direccion = direccionT.getText();
    telefono = telefonoT.getText();

    textos = new String[3];
    textos[0] = nombre;
    textos[1] = direccion;
    textos[2] = telefono;

    objetos = new Object[0]; // Vacío ya que no hay objetos verificables.
  }

  /**
   * Dependiente de la iteración. Puede retornar a la verificación del
   * destinatario o lanzar directamente al registro del envío del paquete.
   */
  private void volver() {
    Globals.pantalla.close();
    if (iteracion == REMITENTE) { // Si el registro es para el remitente del paquete:
      operadorRegister.cedulaRemitente = cedula;
      // ventana = new Ventana("cliente.check", new ClienteCheck(iteracion + 1,
      // operadorRegister));
    } else { // Si el registro era el del destinatario:
      operadorRegister.cedulaDestinatario = cedula;
      ventana = new Ventana("operador.registrar", operadorRegister);
    }

    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
