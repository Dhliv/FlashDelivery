package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Pago;
import utilities.Globals;
import utilities.SobreTarjeta;
import utilities.TextFieldRestrictions;

public class OperadorTarjeta implements Initializable {
  private Integer tipoTarjeta;
  private OperadorResumen befCtr; // Controlador de la vista anterior;
  private model.RegistrarEnvio envio;
  private static final Integer DEBITO = 0;
  private static final Integer CREDITO = 1;

  private String numeroTarjeta;
  private String cvv;
  private String mes;
  private String año;
  private String nombre;
  private Integer counter;
  private Boolean agregar;
  private Boolean borrar;
  private String addToText;

  @FXML private Label lblTipoTarjeta; // label que identifica el tipo de tarjeta con la que se paga.
  @FXML private TextField txtNumerotarjeta; // textField donde se ingresa el numero de la tarjeta
  @FXML private TextField txtTitular; // textField donde se digita el nombre del titular de la tarjeta.
  @FXML private TextField txtCVV; // textField donde se digita el CVV de la tarjeta.
  @FXML private Label lblNumeroCuotas; // label del Texto de numero de cuotas.
  @FXML private DatePicker dateFechaVencimiento; // datePicker que indica la fecha de vencimiento de la tarjeta.
  @FXML private ChoiceBox<Integer> chboxNumeroCuotas; // número de cuotas en las que se pagará (depende de la tarjeta).
  @FXML private Label lblNumero1; // primeros cuatro numeros de la tarjeta.
  @FXML private Label lblNumero2; // segundo cuarteto del numero de la tarjeta.
  @FXML private Label lblNumero3; // tercer cuarteto del numero de la tarjeta.
  @FXML private Label lblNumero4; // cuarteto final del numero de la tarjeta.
  @FXML private Label lblCVV; // cvv para desplegar en pantalla.
  @FXML private Label lblNombreEnTarjeta; // nombre del titular para desplegar en pantalla
  @FXML private Label lblMes; // mes de vencimiento de la tarjeta para desplegar en pantalla.
  @FXML private Label lblAño; // dos ultimos digitos del año de vencimiento de la tarjeta para desplegar en
                              // pantalla.

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
    counter = 0;
  }

  @Override public void initialize(URL location, ResourceBundle resources) {

    lblTipoTarjeta.setText("Tarjeta de " + ((tipoTarjeta == CREDITO) ? "Crédito" : "Debito"));
    lblNumero1.setText("");
    lblNumero2.setText("");
    lblNumero3.setText("");
    lblNumero4.setText("");
    lblCVV.setText("");
    lblNombreEnTarjeta.setText("");
    lblMes.setText("");
    lblAño.setText("");

    if (tipoTarjeta == DEBITO) {
      chboxNumeroCuotas.setVisible(false);
      lblNumeroCuotas.setVisible(false);
    }

    TextFieldRestrictions.textFieldMaxLength(txtNumerotarjeta, 16);
    TextFieldRestrictions.textFieldNumeric(txtNumerotarjeta);

    TextFieldRestrictions.textFieldMaxLength(txtCVV, 4);
    TextFieldRestrictions.textFieldNumeric(txtCVV);
  }

  /**
   * Registra todo lo relacionado al envío en la base de datos y vuelve a la
   * pantalla principal del operador de oficina.
   * 
   * @param event not used.
   */
  @FXML void finalizarPago(ActionEvent event) {

    getData();
    parseData();
    Pago.ejecutarPago(envio);
  }

  /**
   * Vuelve a la vista anterior.
   * 
   * @param event not used.
   */
  @FXML void atras(ActionEvent event) {
    Globals.cambiarVista(Globals.loadView("operador.resumen", befCtr));
  }

  /**
   * Imprime los numeros correspondientes a la tarjeta.
   * @param event not used.
   */
  @FXML void printDigitsTarjeta(KeyEvent event) {
    Object[] validados = SobreTarjeta.checkErase(event, true);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];
    addToText = (String) validados[2];

    if (borrar) {
      if (counter > 0 && counter <= SobreTarjeta.tNTM()) SobreTarjeta.eraseNumber(counter, lblNumero1, lblNumero2, lblNumero3, lblNumero4);
      if (counter > 0) counter--;
    } else if (agregar && counter < SobreTarjeta.tNTM()) {
      SobreTarjeta.addNumber(event.getText(), counter, lblNumero1, lblNumero2, lblNumero3, lblNumero4);
      counter++;
    }
  }

  @FXML void eraseTitular(KeyEvent event) {
    Object[] validados = SobreTarjeta.checkErase(event, false);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];
    addToText = (String) validados[2];

    if (borrar) lblNombreEnTarjeta.setText(SobreTarjeta.eraseFrom(lblNombreEnTarjeta.getText(), 1));
  }

  @FXML void addTitular(KeyEvent event) {
    Object[] validados = SobreTarjeta.checkErase(event, false);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];
    addToText = (String) validados[2];

    if (borrar) return;
    lblNombreEnTarjeta.setText(SobreTarjeta.addTo(lblNombreEnTarjeta.getText(), event.getCharacter()));
  }

  private void getData() {
    nombre = txtTitular.getText();
    numeroTarjeta = txtNumerotarjeta.getText();
    cvv = txtCVV.getText();
    mes = dateFechaVencimiento.getValue().toString();
  }

  private void parseData() {
    año = mes.substring(2, 4);
    mes = mes.substring(5, 7);
  }

}
