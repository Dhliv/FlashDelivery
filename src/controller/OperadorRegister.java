package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utilities.Globals;
import utilities.Ventana;

public class OperadorRegister {
  private Ventana ventana;
  @FXML
  private TextField cedulaClienteT;
  @FXML
  private TextField cedulaDestinoT;
  @FXML
  private TextField direccionEntregaT;
  @FXML
  private ChoiceBox<?> sedeEnvioT;
  @FXML
  private TextField pesoPaqueteT;
  @FXML
  private ChoiceBox<?> metodoPagoT;
  @FXML
  private TextField valorPaqueteT;
  @FXML
  private Label labelImpuesto;
  @FXML
  private Label labelTotalCosto;
  @FXML
  private TextArea descripcionT;
  @FXML
  private CheckBox seguroChoice;
  @FXML
  private Button btnRegistrar;
  @FXML
  private Button btnRegresar;

  @FXML
  void goToOperadorOficina(ActionEvent event) {
    Globals.pantalla.close();
    ventana = new Ventana("operadorOficina", new OperadorOficina());
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void registrarEnvio(ActionEvent event) {

  }

}
