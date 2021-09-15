package controller.operador.envio;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Pago;
import model.Entities.Empleado;
import utilities.SpecificAlerts;
import utilities.Globals;

/**
 * Clase encargada de controlar la vista OperadorResumen Despliega el metodo de
 * pago que se quiere elegir y reenvia a el seleccionado Efectivo = Devolverse a
 * la pantalla principal de operador Credito = Muestra la vista de credito
 * Debito = Muestra la vista de debito
 */
public class OperadorResumen {
  private model.RegistrarEnvio envio;

  private static final Integer DEBITO = 0;
  private static final Integer CREDITO = 1;
  private int total;
  private int impuesto;
  private Empleado operador;
  private Pago pago;

  @FXML
  private Label lblCedulaR; // label Cedula del Remitente
  @FXML
  private Label lblNameR; // label nombre de remitente
  @FXML
  private Label lblCedulaD; // label cedula del destinatario
  @FXML
  private Label lblNameD; // label Nombre del Destinatario
  @FXML
  private Label lblDirD; // direccion del destinatario
  @FXML
  private Label lblnumP; // numero de paquetes en el pedido
  @FXML
  private Label labelCostoEnvio; // costo del envio
  @FXML
  private Label lblImpuesto; // valor del impuesto
  @FXML
  private Label lblSeguro; // Costo de los seguros de los paquetes.
  @FXML
  private Label lblTotal; // Costo total del envío.

  /**
   * Constructor de la clase OperadorResumen.
   * 
   * @param envio Clase que almacena métodos e informacion de los clientes y
   *              paquetes del envío.
   */
  public OperadorResumen(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
  }

  /**
   * Iniciliaza los caomponentes gráficos con los datos del cliente y los costos
   * de su envío.
   */
  public void initialize() throws IOException {
    chargeInformation();
  }

  /**
   * Carga la información relacionada al envío en la interfaz grafica.
   */
  public void chargeInformation() throws IOException { 
    /*
    // Actualiza los datos en pantalla.
    lblCedulaR.setText(lblCedulaR.getText() + ": " + envio.getRemitente().cedula);
    lblNameR.setText(lblNameR.getText() + ": " + envio.getRemitente().nombre);
    lblCedulaD.setText(lblCedulaD.getText() + ": " + envio.getDestinatario().cedula);
    lblNameD.setText(lblNameD.getText() + ": " + envio.getDestinatario().nombre);
    lblDirD.setText(lblDirD.getText() + ": " + envio.getDestinatario().direccion);
    lblnumP.setText(lblnumP.getText() + ": " + Integer.toString(envio.getPaquetes().size()));

    // Calcula el total del envio y su respectivo impuesto.
    pago = new Pago(envio, operador);
    total = pago.getTotal();
    impuesto = pago.getImpuesto();

    // Actualiza los datos en pantalla.
    labelCostoEnvio.setText(labelCostoEnvio.getText() + ": " + Integer.toString(total - impuesto));
    lblImpuesto.setText(lblImpuesto.getText() + ": " + Integer.toString(impuesto));
    lblSeguro.setText(lblSeguro.getText() + ": " + Integer.toString(pago.getSeguro()));
    lblTotal.setText(lblTotal.getText() + ": " + Integer.toString(total));
    */
  }

  /**
   * Vuelve a la pestaña de registro de paquetes.
   * 
   * @param event not used.
   */
  @FXML
  void atras(ActionEvent event) {
    Globals.cambiarVista("operador.paquetes");
  }

  /**
   * Accede a la pantalla de pago con tarjeta. Se deshabilitan componentes
   * gráficos según el tipo de la tarjeta.
   * 
   * @param tipo de la tarjeta.
   */
  void pagar(Integer tipo) {
    Globals.cambiarVista("operador.validar.tarjeta", new OperadorTarjeta(tipo, envio, pago));
  }

  /**
   * Se ejecuta pagar con el tipo de tarjeta de credito.
   * 
   * @param event not used.
   */
  @FXML
  void pagoCredito(ActionEvent event) {
    pagar(CREDITO);
  }

  /**
   * Se ejecuta pagar con el tipo de tarjeta de debito.
   * 
   * @param event not used.
   */
  @FXML
  void pagoDebito(ActionEvent event) {
    pagar(DEBITO);
  }

  /**
   * El pago se hace efectivo (o eso asumimos) y se vuelve a la pantalla principal
   * del Operador de Oficina.
   * 
   * @param event not used.
   */
  @FXML
  void pagoEfectivo(ActionEvent event) {
    pago.ejecutarPago(envio);
    SpecificAlerts.showPagoExitoso();
  }
}
