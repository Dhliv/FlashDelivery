package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.Ventana;

public class OperadorOficina {
  private static final int REMITENTE = 0;

  public Ventana ventana;
  @FXML private TableView<?> tablePaquetes;
  @FXML private TableColumn<?, ?> columnDestinatario;
  @FXML private TableColumn<?, ?> columnIdPaquete;
  @FXML private TableColumn<?, ?> columnPesoPaquete;
  @FXML private TableColumn<?, ?> columnEstado;
  @FXML private Label labelPOS;
  @FXML private Button btnSolicitudRecogida;
  @FXML private Button btnRegistrarPaquete;
  @FXML private Label labelUsuario;
  @FXML private Pane rightContent;

  public void initialize(){
    Globals.viewPane = rightContent;
  }

  /**
   * Accede a la pantalla de registro de paquetes.
   */
  @FXML void registrarEnvio(ActionEvent event) {
    Globals.cambiarVista("operador.cliente");
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
