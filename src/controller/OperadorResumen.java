package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utilities.Globals;

/**
 * Clase encargada de controlar la vista OperadorResumen Despliega el metodo de
 * pago que se quiere elegir y reenvia a el seleccionado Efectivo = Devolverse a
 * la pantalla principal de operador Credito = Muestra la vista de credito
 * Debito = Muestra la vista de debito
 */
public class OperadorResumen implements Initializable {
  private model.RegistrarEnvio envio;

  private static final Double IMPUESTO = 0.19;
  @FXML private Label lblCedulaR; // label Cedula del Remitente
  @FXML private Label lblNameR; // label nombre de remitente
  @FXML private Label lblCedulaD; // label cedula del destinatario
  @FXML private Label lblNameD; // label Nombre del Destinatario
  @FXML private Label lblDirD; // direccion del destinatario
  @FXML private Label lblnumP; // numero de paquetes en el pedido
  @FXML private Label labelCostoEnvio; // costo del envio
  @FXML private Label lblImpuesto; // valor del impuesto
  @FXML private Label lblSeguro; // Costo de los seguros de los paquetes.
  @FXML private Label lblTotal; // Costo total del envío.

  /**
   * Constructor de la clase OperadorResumen.
   * 
   * @param envio Clase que almacena métodos e informacion de los clientes y
   *              paquetes del envío.
   */

  public OperadorResumen(model.RegistrarEnvio envio) {
    this.envio = envio;
  }

  /**
   * Iniciliaza los caomponentes gráficos con los datos del cliente y los costos
   * de su envío.
   */
  @Override public void initialize(URL location, ResourceBundle resources) {

    // Actualiza los datos en pantalla.
    lblCedulaR.setText(lblCedulaR.getText() + ": " + envio.getRemitente().cedula);
    lblNameR.setText(lblNameR.getText() + ": " + envio.getRemitente().nombre);
    lblCedulaD.setText(lblCedulaD.getText() + ": " + envio.getDestinatario().cedula);
    lblNameD.setText(lblNameD.getText() + ": " + envio.getDestinatario().nombre);
    lblDirD.setText(lblDirD.getText() + ": " + envio.getDestinatario().direccion);
    lblnumP.setText(lblnumP.getText() + ": " + Integer.toString(envio.getPaquetes().size()));

    // Calcula el total del envio y su respectivo impuesto.
    int total = calcularTotal(envio);
    int impuesto = calcularImpuesto(total);

    // Actualiza los datos en pantalla.
    labelCostoEnvio.setText(labelCostoEnvio.getText() + ": " + Integer.toString(total - impuesto));
    lblImpuesto.setText(lblImpuesto.getText() + ": " + Integer.toString(impuesto));
    lblTotal.setText(lblTotal.getText() + ": " + Integer.toString(total));
  }

  // TODO Pasarlo a un .java de auxiliares
  public int calcularTotal(model.RegistrarEnvio envio) {
    int total = 0;
    for (int i = 0; i < envio.getPaquetes().size(); i++) {
      total += envio.getPaquetes().get(i).total;
    }
    return total;
  }

  // TODO Pasarlo a un .java de auxiliares
  public int calcularImpuesto(int total) {
    int impuesto = total;
    impuesto *= IMPUESTO;
    return impuesto;
  }

  /**
   * Vuelve a la pestaña de registro de paquetes.
   * 
   * @param event not used.
   */
  @FXML void atras(ActionEvent event) {
    Globals.cambiarVista("operador.paquetes");
  }

  @FXML void btnClickCredito(MouseEvent event) {

  }

  @FXML void btnClickDebito(MouseEvent event) {
    Globals.loadView("operador.validar.tarjeta", new OperadorTarjeta());
  }

  /**
   * El pago se hace efectivo (o eso asumimos) y se vuelve a la pantalla principal
   * del Operador de Oficina.
   * @param event
   */
  @FXML void btnClickEfectivo(MouseEvent event) {

  }
}
