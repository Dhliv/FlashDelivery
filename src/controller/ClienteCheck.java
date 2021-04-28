package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Clientes;
import model.Clientes.Cliente;
import utilities.GeneralAlerts;
import utilities.GeneralChecker;
import utilities.Globals;
import utilities.Ventana;

public class ClienteCheck {
  private String cedula;
  private String[] textos;
  private Object[] objetos;
  private int iteracion;
  private static final int REMITENTE = 0;
  private Ventana ventana;
  private OperadorRegister operadorRegister;

  @FXML
  private TextField cedulaT;

  public ClienteCheck(int it, OperadorRegister controller) {
    iteracion = it;
    operadorRegister = controller;
  }

  @FXML
  void validarCliente(ActionEvent event) {
    getData();

    boolean emptyCamps = GeneralChecker.checkEmpty(textos, objetos);
    boolean forbidchar = GeneralChecker.checkChar(textos);

    if (!(emptyCamps || forbidchar)) {
      boolean existeCliente = Clientes.clientExists(cedula);

      if (!existeCliente)
        goToRegisterCliente();
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

  void getData() {
    cedula = cedulaT.getText();

    textos = new String[1];
    textos[0] = cedula;

    objetos = new Object[0];
  }

  void goToRegisterCliente() {
    Globals.pantalla.close();
    ventana = new Ventana("cliente.registrar", new ClienteRegister(cedula, iteracion, operadorRegister));
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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
