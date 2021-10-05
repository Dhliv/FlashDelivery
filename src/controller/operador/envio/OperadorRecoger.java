package controller.operador.envio;

import controller.controls.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Entities.Empleado;
import utilities.View;

/**
 * Controlador para la vista operador.cliente
 * 
 * Copia casi identica de RegistrarCientes
 * 
 * @author Alejandro Pergueza
 * @version 0.1, 25/09/2021
 */
public class OperadorRecoger {
  @FXML
  private Cliente remitente;
  @FXML
  private Cliente destinatario;
  @FXML
  private Label lblTipoInterfaz;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public OperadorRecoger(Empleado operador) {
    envio = new model.RegistrarEnvio();
    this.operador = operador;

  }

  public void initialize() {

    remitente.initialize(envio);
    destinatario.initialize(envio);
    lblTipoInterfaz.setText("Recoger un paquete");
    View.setViewPane(View.getViewPane());
  }

  /**
   * Verificar los campos, actualizar los valores en envio, y continuar a la vista
   * operador.paquetes
   */
  @FXML
  void registrarPaquetes(ActionEvent event) {
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

    View.cambiar("operador.recoger", new RecogerPaquete(envio, operador));
  }
}
