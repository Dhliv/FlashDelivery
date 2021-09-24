package controller.operador.envio;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Pago;
import utilities.GeneralChecker;
import utilities.SpecificAlerts;
import utilities.SobreTarjeta;
import utilities.TextFieldRestrictions;
import utilities.View;
import model.Entities.Empleado;

/**
 * Clase controller OperadorTarjeta. Contiene parámetros de front y back end
 * relacionados a una tarjeta. Posee diversos métodos para el manejo visual de
 * los datos insertados por el usuario, ademas de control sobre lo que el
 * usuario digita.
 * 
 * @author David Henao
 * @author Alejandro Pergueza Amaya
 * @version 1.0
 * @since 24/09/2021
 */
public class OperadorTarjeta implements Initializable {

  private Integer CVVLENGTH = 4;
  private Integer CARDLENGTH = 16;
  private Integer tipoTarjeta; // Almacena el tipo de la tarjeta que se haya seleccionado para el método de
                               // pago.
  private model.RegistrarEnvio envio; // Almacena la info relacionada al envio (destinatario, remitente, paquetes,
                                      // etc).
  private static final Integer DEBITO = 0; // Identifica un tipo de tarjeta.
  private static final Integer CREDITO = 1; // Identifica un tipo de tarjeta.
  private Boolean camposVacios; // Almacena si existen campos vacios.
  private Boolean forbidChar; // Almacena si existen caracteres prohibidos.
  private String[] campos; // Almacena los strings a los que se ejecutarán validaciones.
  private Object[] objetos; // Almacena los objetos a los que se ejecutarán validaciones.

  private String numeroTarjeta; // Almacena el número de la tarjeta.
  private String cvv; // Almacena el núremo CVV de la tarjeta.
  private String mes; // Almacena el mes de vencimiento de la tarjeta.
  private String nombre; // Almacena el nombre del titular de la tarjeta.
  private Object nCuotas; // Almacena la informacion del CheckBox de número de cuotas.
  private Integer counter; // Auxiliar que almacena el número de digitos en el campo de texto del numero de
                           // tarjeta.
  private Boolean agregar; // Auxiliar que almacena si de debe agregar o no un caracter a la parte gráfica.
  private Boolean borrar; // Auxiliar que almacena si de debe borrar o no un caracter a la parte gráfica.
  private Object mesAux; // Almacena la información relacionada a la fecha de vencimiento de la tarjeta.
  private Pago pago; // Almacena toda la info relacionada con el objeto pago.
  private Empleado operador; // Almacena al operador de oficina.

  @FXML
  private Label lblTipoTarjeta; // label que identifica el tipo de tarjeta con la que se paga.
  @FXML
  private TextField txtNumerotarjeta; // textField donde se ingresa el numero de la tarjeta
  @FXML
  private TextField txtTitular; // textField donde se digita el nombre del titular de la tarjeta.
  @FXML
  private TextField txtCVV; // textField donde se digita el CVV de la tarjeta.
  @FXML
  private Label lblNumeroCuotas; // label del Texto de numero de cuotas.
  @FXML
  private DatePicker dateFechaVencimiento; // datePicker que indica la fecha de vencimiento de la tarjeta.
  @FXML
  private ChoiceBox<String> chboxNumeroCuotas; // número de cuotas en las que se pagará (depende de la tarjeta).
  @FXML
  private Label lblNumero1; // primeros cuatro numeros de la tarjeta.
  @FXML
  private Label lblNumero2; // segundo cuarteto del numero de la tarjeta.
  @FXML
  private Label lblNumero3; // tercer cuarteto del numero de la tarjeta.
  @FXML
  private Label lblNumero4; // cuarteto final del numero de la tarjeta.
  @FXML
  private Label lblCVV; // cvv para desplegar en pantalla.
  @FXML
  private Label lblNombreEnTarjeta; // nombre del titular para desplegar en pantalla
  @FXML
  private Label lblMes; // mes de vencimiento de la tarjeta para desplegar en pantalla.
  @FXML
  private Label lblAño; // dos ultimos digitos del año de vencimiento de la tarjeta para desplegar en
                        // pantalla.

  /**
   * Constructor de la clase OperadorTarjeta.
   * 
   * @param tipoTarjeta identifica el número de tarjeta a usar.
   * @param envio       Almacena la información relacionada al envío
   *                    (destinatario, remitente, paquetes, etc).
   * @param pago        OBjeto de pago con toda la información relacionada a éste.
   */
  public OperadorTarjeta(Integer tipoTarjeta, model.RegistrarEnvio envio, Pago pago, Empleado operador) {
    this.tipoTarjeta = tipoTarjeta;
    this.pago = pago;
    this.envio = envio;
    this.operador = operador;
    counter = 0;
  }

  /**
   * Inicializa los componentes gráficos. Además establece restricciones a los
   * campos de texto necesarios.
   * 
   * @param location  not used.
   * @param resources not used.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> numeroDeCuotas = FXCollections.observableArrayList();
    ArrayList<String> aux = new ArrayList<>();
    for (int i = 1; i <= 36; i++) {
      aux.add(String.valueOf(i));
    }
    numeroDeCuotas.removeAll(numeroDeCuotas);
    numeroDeCuotas.addAll(aux);
    chboxNumeroCuotas.getItems().addAll(numeroDeCuotas);

    // Muestra en pantalla si es una tarjeta de credito o de debito respectivamente.
    lblTipoTarjeta.setText("Tarjeta de " + ((tipoTarjeta == CREDITO) ? "Crédito" : "Debito"));

    lblNumero1.setText("");
    lblNumero2.setText("");
    lblNumero3.setText("");
    lblNumero4.setText("");
    lblCVV.setText("");
    lblNombreEnTarjeta.setText("");
    lblMes.setText("");
    lblAño.setText("");

    // Oculta el campo numero de cuotas.
    if (tipoTarjeta == DEBITO) {
      chboxNumeroCuotas.setVisible(false);
      lblNumeroCuotas.setVisible(false);
    }

    TextFieldRestrictions.textFieldMaxLength(txtNumerotarjeta, CARDLENGTH);
    TextFieldRestrictions.textFieldNumeric(txtNumerotarjeta);

    TextFieldRestrictions.textFieldMaxLength(txtCVV, CVVLENGTH);
    TextFieldRestrictions.textFieldNumeric(txtCVV);

    TextFieldRestrictions.textFieldAlphabeticChars(txtTitular);
  }

  /**
   * Registra todo lo relacionado al envío en la base de datos y vuelve a la
   * pantalla principal del operador de oficina.
   * 
   * @param event not used.
   */
  @FXML
  void finalizarPago(ActionEvent event) {
    getData();
    camposVacios = GeneralChecker.checkEmpty(campos, objetos);
    forbidChar = GeneralChecker.checkChar(campos);
    Boolean pagar = true; // Booleano para ver si se puede efectuar el pago adecuadamente o existe algún
                          // problema.

    // Revisa que tenga como minimo CVVLENGTH de tamaño el string escrito por el
    // usuario en el campo CVV
    if (cvv.length() < CVVLENGTH) {
      SpecificAlerts.showCardUnexist();
      pagar = false;
    }

    // Revisa lo mismo que en el anterior pero en el campo numeroTarjeta
    if (numeroTarjeta.length() < CARDLENGTH) {
      SpecificAlerts.showCardUnexist();
      pagar = false;
    }

    if (!(camposVacios || forbidChar) && pagar) {
      parseData();
      pago.ejecutarPago(envio, tipoTarjeta == CREDITO ? "Credito" : "Debito");
      SpecificAlerts.showPagoExitoso();
      goBack();
    } else {
      if (camposVacios)
        SpecificAlerts.showEmptyFieldAlert();
      else
        SpecificAlerts.showCharForbidenAlert();
    }
  }

  /**
   * Vuelve a la vista anterior.
   * 
   * @param event not used.
   */
  @FXML
  void atras(ActionEvent event) {
    View.newView("operador.resumen", new OperadorResumen(envio, operador));
  }

  /**
   * Imprime los numeros correspondientes a la tarjeta.
   * 
   * @param event not used.
   */
  @FXML
  void printDigitsTarjeta(KeyEvent event) {
    Object[] validados = SobreTarjeta.checkErase(event, true);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];

    if (borrar) {
      if (counter > 0 && counter <= SobreTarjeta.tNTM())
        SobreTarjeta.eraseNumber(counter, lblNumero1, lblNumero2, lblNumero3, lblNumero4);
      if (counter > 0)
        counter--;
    } else if (agregar && counter < SobreTarjeta.tNTM()) {
      SobreTarjeta.addNumber(event.getText(), counter, lblNumero1, lblNumero2, lblNumero3, lblNumero4);
      counter++;
    }
  }

  /**
   * Borra un caracter del componente gráfico que muestra el nombre del titular de
   * la tarjeta, si es el caso.
   * 
   * @param event not used.
   */
  @FXML
  void eraseTitular(KeyEvent event) {
    Object[] validados = SobreTarjeta.checkErase(event, false);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];

    if (borrar && txtTitular.getText().length() < 21)
      lblNombreEnTarjeta.setText(SobreTarjeta.eraseFrom(lblNombreEnTarjeta.getText(), 1));
  }

  /**
   * Agrega un caracter al componente gráfico que muestra el nombre del titular de
   * la tarjeta, si es el caso.
   * 
   * @param event not used.
   */
  @FXML
  void addTitular(KeyEvent event) {

    Object[] validados = SobreTarjeta.checkErase(event, false);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];

    System.out.println(event.getCharacter().codePointAt(0));
    if ((borrar || event.getCharacter().codePointAt(0) == 8) && txtTitular.getText().length() < 21) {
      return;
    }

    // Revisa que se cumpla la misma condición del txtField en el componente grafico
    // que imprime la cadena
    if (agregar && Character.isDefined(event.getCharacter().charAt(0)) && lblNombreEnTarjeta.getText().length() < 21)
      lblNombreEnTarjeta.setText(SobreTarjeta.addTo(lblNombreEnTarjeta.getText(), event.getCharacter()));
  }

  /**
   * Agrega o elimina un caracter al componente gráfico que muestra el cvv si es
   * el caso.
   * 
   * @param event not used
   */
  @FXML
  void addCvv(KeyEvent event) {
    // lblCVV
    Object[] validados = SobreTarjeta.checkErase(event, true);
    borrar = (Boolean) validados[0];
    agregar = (Boolean) validados[1];

    if (borrar) {
      lblCVV.setText(SobreTarjeta.eraseFrom(lblCVV.getText(), 1));
    }

    if (agregar && lblCVV.getText().length() < CVVLENGTH)
      lblCVV.setText(SobreTarjeta.addTo(lblCVV.getText(), event.getText()));
  }

  /**
   * Agrega o elimina un caracter al componente gráfico que muestra la fecha si es
   * el caso.
   * 
   * @param event not used.
   */
  @FXML
  void addFecha(ActionEvent event) {

    int month = dateFechaVencimiento.getValue().getMonthValue();
    if (month < 10)
      lblMes.setText("0" + Integer.toString(month));
    else
      lblMes.setText(Integer.toString(month));
    lblAño.setText(Integer.toString(dateFechaVencimiento.getValue().getYear()));
  }

  /**
   * Se obtienen los datos de los campos de texto.
   */
  private void getData() {
    nombre = txtTitular.getText();
    numeroTarjeta = txtNumerotarjeta.getText();
    cvv = txtCVV.getText();
    mesAux = dateFechaVencimiento.getValue();

    campos = new String[3];
    objetos = new Object[1 + tipoTarjeta];
    campos[0] = nombre;
    campos[1] = numeroTarjeta;
    campos[2] = cvv;
    objetos[0] = mesAux;
    if (tipoTarjeta == CREDITO) {
      nCuotas = chboxNumeroCuotas.getValue();
      objetos[1] = nCuotas;
    }
  }

  /**
   * Obtiene el texto de los campos que no son explicitamente de texto.
   */
  private void parseData() {
    mes = mesAux.toString();
    mes = mes.substring(5, 7);
  }

  /**
   * Vuelve a la pantalla principal de operador de oficina.
   */
  private void goBack() {
    View.clearViews();
    View.cambiar("operador.cliente", new RegistrarClientes(operador));
  }
}
