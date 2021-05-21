package controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Envios;
import model.Paquetes;
import utilities.Globals;

/**
 * Clase encargada de controlar la vista OperadorResumen Despliega el metodo de
 * pago que se quiere elegir y reenvia a el seleccionado Efectivo = Devolverse a
 * la pantalla principal de operador Credito = Muestra la vista de credito
 * Debito = Muestra la vista de debito
 */
public class OperadorResumen {
  private model.RegistrarEnvio envio;
  private RegistrarEnvio befCtr;

  private static final Double IMPUESTO = 0.19;
  private static final Integer DEBITO = 0;
  private static final Integer CREDITO = 1;
  private Integer total;
  private Integer impuesto;
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

  public void update(model.RegistrarEnvio envio, RegistrarEnvio ah) {
    this.envio = envio;
    befCtr = ah;
  }

  /**
   * Iniciliaza los caomponentes gráficos con los datos del cliente y los costos
   * de su envío.
   */
  public void initialize() {
    chargeInformation();
  }

  public void setEnvio(model.RegistrarEnvio r) {

  }

  public void chargeInformation() {
    // Actualiza los datos en pantalla.
    lblCedulaR.setText(lblCedulaR.getText() + ": " + envio.getRemitente().cedula);
    lblNameR.setText(lblNameR.getText() + ": " + envio.getRemitente().nombre);
    lblCedulaD.setText(lblCedulaD.getText() + ": " + envio.getDestinatario().cedula);
    lblNameD.setText(lblNameD.getText() + ": " + envio.getDestinatario().nombre);
    lblDirD.setText(lblDirD.getText() + ": " + envio.getDestinatario().direccion);
    lblnumP.setText(lblnumP.getText() + ": " + Integer.toString(envio.getPaquetes().size()));

    // Calcula el total del envio y su respectivo impuesto.
    total = calcularTotal(envio);
    impuesto = calcularImpuesto(total);

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
    Globals.cambiarVista(Globals.loadView("operador.paquetes", befCtr));
  }

  void pagar(Integer tipo) {
    Globals.cambiarVista(Globals.loadView("operador.validar.tarjeta", new OperadorTarjeta(tipo, this, envio)));
  }

  @FXML void pagoCredito(ActionEvent event) {
    pagar(CREDITO);
  }

  @FXML void pagoDebito(ActionEvent event) {
    pagar(DEBITO);
  }

  /**
   * El pago se hace efectivo (o eso asumimos) y se vuelve a la pantalla principal
   * del Operador de Oficina.
   * @param event
   */
  @FXML void pagoEfectivo(ActionEvent event) {
    Integer SEDE = Globals.empleado.getSede();
    String EMPLEADO = Globals.empleado.getCedula();
    Date DATE = Date.valueOf(LocalDate.now());

    Integer idEnvio = Envios.createEnvio(DATE, "Efectivo", total, Boolean.FALSE, impuesto, envio.getDestinatario().direccion, SEDE, EMPLEADO, envio.getRemitente().cedula, envio.getDestinatario().cedula);
    Paquetes.createPaquetes(envio.getPaquetes(), idEnvio);
  }

}
