package controller.operador.envio;

import javax.swing.JOptionPane;

import controller.controls.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Entities.Empleado;
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

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarClientes(Empleado operador) {
    envio = new model.RegistrarEnvio();
    this.operador = operador;
  }

  public void initialize() {
    destinatario.initialize(envio);
    remitente.initialize(envio);
  }

  /**
   * Verificar los campos, actualizar los valores en envio, y continuar a la vista
   * operador.paquetes
   */
  @FXML
  void registrarPaquetes(ActionEvent event) {
    if (!remitente.checkAndUpdateEnvio())
      return;
    if (!destinatario.checkAndUpdateEnvio())
      return;
    View.cambiar("operador.paquetes", new RegistrarPaquete(envio, operador));
  }
}
