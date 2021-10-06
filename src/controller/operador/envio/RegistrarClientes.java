package controller.operador.envio;

import javax.swing.JOptionPane;

import controller.controls.Cliente;
import controller.operador.verPaquetes.OperadorConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Entities.Empleado;
import utilities.View;

/**
 * Controlador para la vista operador.cliente
 * 
 * @author Julián Orejuela
 * @version 1.3, 21/09/2021
 */
public class RegistrarClientes {
  @FXML private Cliente remitente;
  @FXML private Cliente destinatario;
  @FXML private Label lblTipoInterfaz;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarClientes(Empleado operador) {
    envio = new model.RegistrarEnvio();
    this.operador = operador;
  }

  public void initialize() {
    lblTipoInterfaz.setText("Registro envío");
    remitente.initialize(envio);
    destinatario.initialize(envio);
  }
  @FXML
  void atras(ActionEvent event) {
    View.cambiarFull("operadorOficinaTabla", new OperadorConsulta(operador));
  }
  /**
   * Verificar los campos, actualizar los valores en envio, y continuar a la vista operador.paquetes
   */
  @FXML void registrarPaquetes(ActionEvent event) {
    remitente.stopBusqueda();
    destinatario.stopBusqueda();
    while (remitente.st.isRunning() || destinatario.st.isRunning()) {
    }
    if (!remitente.checkAndUpdateEnvio()) {
      remitente.restart();
      destinatario.restart();
      return;
    }
    if (!destinatario.checkAndUpdateEnvio()) {
      remitente.restart();
      destinatario.restart();
      return;
    }

    System.out.println("Registrar cliente: " + envio.getDestinatario().cedula);
    View.cambiar("operador.paquetes", new RegistrarPaquete(envio, operador));

  }
}
