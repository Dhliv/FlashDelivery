package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilities.Globals;

public class OperadorTarjeta {
  private Integer tipoTarjeta;
  private OperadorResumen befCtr; // Controlador de la vista anterior;
  private model.RegistrarEnvio envio;

  @FXML private Label lblTipoTarjeta; // label que identifica el tipo de tarjeta con la que se paga.
  @FXML private TextField txtNumerotarjeta; // textField donde se ingresa el numero de la tarjeta
  @FXML private TextField txtTitular; // textField donde se digita el nombre del titular de la tarjeta.
  @FXML private TextField txtCVV; // textField donde se digita el CVV de la tarjeta.
  @FXML private DatePicker dateFechaVencimiento; // datePicker que indica la fecha de vencimiento de la tarjeta.
  @FXML private ChoiceBox<Integer> chboxNumeroCuotas; // número de cuotas en las que se pagará (depende de la tarjeta).
  @FXML private Label lblNumero1;
  @FXML private Label lblNumero2;
  @FXML private Label lblNumero3;
  @FXML private Label lblNumero4;
  @FXML private Label lblCVV;
  @FXML private Label lblNombreEnTarjeta;
  @FXML private Label lblMes;
  @FXML private Label lblAño;
  @FXML private Label lblAño1;

  /**
   * Constructor de la clase OperadorTarjeta.
   * 
   * @param tipoTarjeta identifica el tipo de tarjeta con la que se pagará.
   * @param ctr         es el controlador de la vista anterior a esta.
   */
  public OperadorTarjeta(Integer tipoTarjeta, OperadorResumen ctr, model.RegistrarEnvio envio) {
    this.tipoTarjeta = tipoTarjeta;
    befCtr = ctr;
    this.envio = envio;
  }

  /**
   * Registra todo lo relacionado al envío en la base de datos y vuelve a la
   * pantalla principal del operador de oficina.
   * 
   * @param event not used.
   */
  @FXML void finalizarPago(ActionEvent event) {

    // Solucion temporal:
    Globals.cambiarVista(Globals.loadView("operadorOficina", new OperadorOficina()));
  }

  /**
   * Vuelve a la vista anterior.
   * 
   * @param event not used.
   */
  @FXML void atras(ActionEvent event) {
    Globals.cambiarVista(Globals.loadView("operador.resumen", befCtr));
  }
}
