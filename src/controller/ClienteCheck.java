package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Clientes;
import utilities.GeneralAlerts;
import utilities.GeneralChecker;
import utilities.Globals;
import utilities.Ventana;

public class ClienteCheck {
  private String cedula; // cedula del cliente
  private String[] textos; // se compone solo de la cedula del cliente
  private Object[] objetos; // conjunto de objetos vacíos para las validaciones
  private int iteracion; // Distingue a quien estoy verificando: remitente o destinatario
  private static final int REMITENTE = 0; // distingue la iteracion
  private Ventana ventana; // objeto para cargar la siguiente ventana
  private OperadorRegister operadorRegister; // controlador de la clase OperadorRegister

  @FXML
  private TextField cedulaT;

  /**
   * Constructor de la clase ClienteCheck.
   * 
   * @param it         iteracion en la que se encuentra el programa.
   * @param controller controlador de la clase OperadorRegister.
   */
  public ClienteCheck(int it, OperadorRegister controller) {
    iteracion = it;
    operadorRegister = controller;
  }

  /**
   * Verifica si el cliente remitente o destinatario está registrado en la base de
   * datos.
   * 
   * @param event not used.
   */
  @FXML
  void validarCliente(ActionEvent event) {
    getData();

    boolean emptyCamps = GeneralChecker.checkEmpty(textos, objetos);
    boolean forbidchar = GeneralChecker.checkChar(textos);

    if (!(emptyCamps || forbidchar)) {
      boolean existeCliente = Clientes.clientExists(cedula); // Verificamos si existe el cliente mediante su cedula.

      if (!existeCliente) // Si el cliente no existe:
        goToRegisterCliente(); // Se dirige a la pantalla de registro de cliente.
      else { // Si el cliente ya existía:
        // Se debe mostrar la alerta de existencia de usuario.

        if (iteracion == REMITENTE) {
          operadorRegister.cedulaRemitente = cedula;
          cedulaT.setText("");
          // mostrar alerta de registrar al cliente destinatario.
          iteracion++;
        } else {
          operadorRegister.cedulaDestinatario = cedula;
          goToRegisterPacket();
        }
      }
    } else {
      if (emptyCamps)
        GeneralAlerts.showEmptyFieldAlert();
      else
        GeneralAlerts.showCharForbidenAlert();
    }
  }

  /**
   * Obtiene los datos de los campos de la ventana.
   */
  void getData() {
    cedula = cedulaT.getText();

    textos = new String[1];
    textos[0] = cedula;

    objetos = new Object[0];
  }

  /**
   * Dirige a la ventana de registrar cliente.
   */
  void goToRegisterCliente() {
    Globals.pantalla.close();
    ventana = new Ventana("cliente.registrar", new ClienteRegister(cedula, iteracion, operadorRegister));
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Dirige a la ventana para registrar el envio de un paquete.
   */
  void goToRegisterPacket() {
    Globals.pantalla.close();
    ventana = new Ventana("operador.registrar", operadorRegister);
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
