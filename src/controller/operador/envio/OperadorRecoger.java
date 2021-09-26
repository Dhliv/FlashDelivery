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

    remitente.initialize(envio, new Thread());
    destinatario.initialize(envio, new Thread());
    lblTipoInterfaz.setText("Recoger un paquete");
    View.setViewPane(View.getViewPane(), false);
  }

  /**
   * Verificar los campos, actualizar los valores en envio, y continuar a la vista
   * operador.paquetes
   */
  @FXML
  void registrarPaquetes(ActionEvent event) {
    while (remitente.t.isAlive() || destinatario.t.isAlive()) {
      System.out.println(remitente.t.isAlive());
      System.out.println(destinatario.t.isAlive());
    }
    if (!remitente.checkAndUpdateEnvio())
      return;
    if (!destinatario.checkAndUpdateEnvio())
      return;

    View.cambiar("operador.recoger", new RecogerPaquete(envio, operador));
  }
}
