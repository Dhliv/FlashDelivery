package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import utilities.Globals;

public class OperadorOficina {
  @FXML
  private Label labelPOS;
  @FXML
  private Button btnSolicitudRecogida;
  @FXML
  private Button btnRegistrarPaquete;
  @FXML
  private Label labelUsuario;
  @FXML
  private Pane rightContent;

  public void initialize() {
    Globals.viewPane = rightContent;
    /* Globals.cambiarVista("operadorOficinaTabla", new OperadorConsulta() ); */
  }

  /**
   * Accede a la pantalla de registro de paquetes.
   */
  @FXML
  void registrarEnvio(ActionEvent event) {
    Globals.cambiarVista("operador.cliente");
  }

  @FXML
  void registrarRecogida(ActionEvent event) {

  }

  /**
   * Vuelve al login.
   */
  @FXML
  void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
