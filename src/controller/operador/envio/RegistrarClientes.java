package controller.operador.envio;

import javax.swing.JOptionPane;

import controller.controls.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Entities.Empleado;
import utilities.Globals;
import utilities.View;

/**
 * Controlador para la vista operador.cliente
 * 
 * @author Julián Orejuela
 * @version 1.3, 21/09/2021
 */
public class RegistrarClientes {
  @FXML
  private Cliente remitente;
  @FXML
  private Cliente destinatario;
  @FXML
  private Label lblTipoInterfaz;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarClientes(Empleado operador) {
    envio = new model.RegistrarEnvio();
    this.operador = operador;
  }

  public void initialize() {
    lblTipoInterfaz.setText("Registro envío");
    remitente.initialize(envio, new Thread());
    destinatario.initialize(envio, new Thread());

    View.setViewPane(View.getViewPane());
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
    System.out.println("Registrar cliente: " + envio.getDestinatario().cedula);
    View.cambiar("operador.paquetes", new RegistrarPaquete(envio, operador));

  }
}
