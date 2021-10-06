package controller.operador.envio;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Pago;
import model.Entities.Empleado;
import utilities.SpecificAlerts;
import utilities.View;
import utilities.Globals;

/**
 * Clase encargada de controlar la vista OperadorResumen. Despliega el metodo de
 * pago que se quiere elegir y reenvia a el seleccionado Efectivo = Devolverse a
 * la pantalla principal de operador Credito = Muestra la vista de credito
 * Debito = Muestra la vista de debito.
 * 
 * @author David Henao
 * @author Alejandro Pergueza Amaya
 * @version 1.0
 * @since 24/09/2021
 */
public class OperadorResumen {
  private model.RegistrarEnvio envio;

  private static final Integer DEBITO = 0;
  private static final Integer CREDITO = 1;
  private Double total;
  private Double impuesto;
  private Double seguro;
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
   * Iniciliaza los componentes gráficos con los datos del cliente y los costos de
   * su envío.
   */
  public void initialize() throws IOException {
    chargeInformation();
  }

  /**
   * Carga la información relacionada al envío en la interfaz grafica.
   */
  public void chargeInformation() throws IOException {
    System.out.println(envio.getDestinatario().cedula);
    lblCedulaR.setText(lblCedulaR.getText() + ": " + envio.getRemitente().cedula);
    lblNameR.setText(lblNameR.getText() + ": " + envio.getRemitente().nombre);
    lblCedulaD.setText(lblCedulaD.getText() + ": " + envio.getDestinatario().cedula);
    lblNameD.setText(lblNameD.getText() + ": " + envio.getDestinatario().nombre);
    lblDirD.setText(lblDirD.getText() + ": " + envio.getDestinatario().direccion);

    // Calcula el total del envio y su respectivo impuesto.
    pago = new Pago(envio, operador);
    total = pago.getTotal();
    impuesto = pago.getImpuesto();
    seguro = envio.getPaquete().seguro ? pago.getSeguro() : 0;

    // Actualiza los datos en pantalla.
    labelCostoEnvio
        .setText(labelCostoEnvio.getText() + ": " + Double.toString(Globals.roundAvoid(total - impuesto - seguro, 2)));
    lblImpuesto.setText(lblImpuesto.getText() + ": " + Double.toString(Globals.roundAvoid(impuesto, 2)));
    lblSeguro.setText(lblSeguro.getText() + ": " + Double.toString(Globals.roundAvoid(seguro, 2)));
    lblTotal.setText(lblTotal.getText() + ": " + Double.toString(Globals.roundAvoid(total, 2)));
  }

  /**
   * Vuelve a la pestaña de registro de paquetes.
   * 
   * @param event not used.
   */
  @FXML
  void atras(ActionEvent event) {
    View.cambiar("operador.paquetes");
  }

  /**
   * Accede a la pantalla de pago con tarjeta. Se deshabilitan componentes
   * gráficos según el tipo de la tarjeta.
   * 
   * @param tipo de la tarjeta.
   */
  void pagar(Integer tipo) {
    View.newView("operador.validar.tarjeta", new OperadorTarjeta(tipo, envio, pago, operador));
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
    pago.ejecutarPago(envio, "Efectivo");
    SpecificAlerts.showPagoExitoso();
    goBack();
  }

  /**
   * Vuelve a la pantalla principal de operador de oficina.
   */
  private void goBack() {
    View.clearViews();
    View.cambiar("operador.cliente", new RegistrarClientes(operador));
  }
}
