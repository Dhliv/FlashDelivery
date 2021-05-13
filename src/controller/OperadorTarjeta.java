package controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.jooq.False;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilities.Globals;
import utilities.Ventana;

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

    // Solucion temporal:
    /*Globals.pantalla.close();
    Ventana v = new Ventana("operadorOficina", null);
    try {
      v.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }

  /**
   * Vuelve a la vista anterior.
   * 
   * @param event not used.
   */
  @FXML void atras(ActionEvent event) {
    Globals.cambiarVista(Globals.loadView("operador.resumen", befCtr));
  }

  @FXML void eraseNumeroTarjeta(KeyEvent event) {
    checkErase(event);
    if (!agregar) {
      if (counter > 0 && counter <= 16) eraseNumber();
      counter--;
    }
  }

  @FXML void addNumeroTarjeta(KeyEvent event) {
    if (!agregar) return;
    if (counter < 16) addNumber(event.getCharacter());
    counter++;
  }

  @FXML void eraseTitular(KeyEvent event) {
    checkErase(event);
    if (!agregar) lblNombreEnTarjeta.setText(eraseFrom(lblNombreEnTarjeta.getText(), 1));
  }

  @FXML void addTitular(KeyEvent event) {
    if (!agregar) return;
    lblNombreEnTarjeta.setText(addTo(lblNombreEnTarjeta.getText(), event.getCharacter()));
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

  /**
   * Agrega un numero a la parte visual de la tarjeta.
   * 
   * @param n el numero a agregar.
   */
  private void addNumber(String n) {
    if (counter < 4) {
      lblNumero1.setText(addTo(lblNumero1.getText(), n + " "));
    } else if (counter < 8) {
      lblNumero2.setText(addTo(lblNumero2.getText(), n + " "));
    } else if (counter < 12) {
      lblNumero3.setText(addTo(lblNumero3.getText(), n + " "));
    } else {
      lblNumero4.setText(addTo(lblNumero4.getText(), n + " "));
    }
  }

  /**
   * Borra numeros de la parte visual de la tarjeta.
   */
  private void eraseNumber() {
    if (counter > 12) {
      lblNumero4.setText(eraseFrom(lblNumero4.getText(), 2));
    } else if (counter > 8) {
      lblNumero3.setText(eraseFrom(lblNumero3.getText(), 2));
    } else if (counter > 4) {
      lblNumero2.setText(eraseFrom(lblNumero2.getText(), 2));
    } else {
      lblNumero1.setText(eraseFrom(lblNumero1.getText(), 2));
    }
  }

  /**
   * Borra un número de caracteres al final de un string.
   * 
   * @param s string al que borrarle caracteres.
   * @param n numero de caracteres a borrar.
   * @return string con caracteres borrados.
   */
  private String eraseFrom(String s, Integer n) {
    return s.substring(0, s.length() - n);
  }

  /**
   * Concatena dos strings.
   * 
   * @param s   String a
   * @param add String b
   * @return a concatenado b
   */
  private String addTo(String s, String add) {
    return (s + add);
  }

  private void checkErase(KeyEvent event) {
    agregar = true;
    KeyCode key = event.getCode();
    if (key.equals(KeyCode.BACK_SPACE)) agregar = false;
  }
}
