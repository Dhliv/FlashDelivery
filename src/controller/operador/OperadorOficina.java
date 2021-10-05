package controller.operador;

import controller.operador.envio.OperadorRecoger;
import controller.operador.envio.RecogerPaquete;
import controller.operador.envio.RegistrarClientes;
import controller.operador.verPaquetes.OperadorConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Entities.Empleado;
import utilities.Globals;
import utilities.View;

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
    View.setViewPane(rightContent, true);
    View.cambiar("operadorOficinaTabla", new OperadorConsulta(operador));
    labelUsuario.setText("Bienvenido " + operador.getNombres());
    labelPOS.setText("Punto de venta " + operador.getSede());
    // View.cambiar("operador.cliente", new RegistrarClientes(operador));

  }

  /**
   * Accede a la pantalla de registro de paquetes.
   */
  @FXML void registrarEnvio(ActionEvent event) {
    View.cambiarFull("operador.cliente", new RegistrarClientes(operador));
  }

  @FXML void registrarRecogida(ActionEvent event) {
    View.cambiarFull("operador.cliente", new OperadorRecoger(operador));
  }

  /**
   * Vuelve al login.
   */
  @FXML void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
