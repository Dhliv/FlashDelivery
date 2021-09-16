package controller.operador;

import controller.operador.envio.RegistrarClientes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Entities.Empleado;
import utilities.Globals;

public class OperadorOficina {
  @FXML private Label labelPOS;
  @FXML private Button btnSolicitudRecogida;
  @FXML private Button btnRegistrarPaquete;
  @FXML private Label labelUsuario;
  @FXML private Pane rightContent;
  private Empleado operador;

  public OperadorOficina(Empleado operador) {
    this.operador = operador;
  }

  public void initialize() {
    Globals.viewPane = rightContent;
    // Globals.cambiarVista("operadorOficinaTabla", new OperadorConsulta(operador));
    Globals.cambiarVista("operador.cliente", new RegistrarClientes(operador));
  }

  /**
   * Accede a la pantalla de registro de paquetes.
   */
  @FXML void registrarEnvio(ActionEvent event) {
    Globals.cambiarVista("operador.cliente", new RegistrarClientes(operador));
    // Globals.cambiarVista("operador.cliente");
  }

  @FXML void registrarRecogida(ActionEvent event) {

  }

  /**
   * Vuelve al login.
   */
  @FXML void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
