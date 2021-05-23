package model;

import java.sql.Date;
import java.time.LocalDate;

import controller.OperadorConsulta;
import utilities.Globals;

public class Pago {
  private static Integer total;
  private static Integer impuesto;
  private static final Double IMPUESTO = 0.19;
  private static final Double SEGURO = 0.06;

  /**
   * Inicializa los valores del total e impuesto del envio.
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void initialize(RegistrarEnvio envio) {
    calcularTotal(envio);
    calcularImpuesto();
  }

  /**
   * Ingresa los datos pertinentes a la base de datos, como los clientes, los
   * paquetes y el envío.
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void ejecutarPago(RegistrarEnvio envio) {
    Integer SEDE = Globals.empleado.getSede();
    String EMPLEADO = Globals.empleado.getCedula();
    Date DATE = Date.valueOf(LocalDate.now());

    Integer idEnvio = Envios.createEnvio(DATE, "Efectivo", total, Boolean.FALSE, impuesto, envio.getDestinatario().direccion, SEDE, EMPLEADO, envio.getRemitente().cedula, envio.getDestinatario().cedula);
    Paquetes.createPaquetes(envio.getPaquetes(), idEnvio);

    goBack();
  }

  /**
   * Vuelve a la pantalla principal del Operador de Oficina.
   */
  private static void goBack() {
    Globals.clearViews();
    Globals.cambiarVista("operadorOficinaTabla", new OperadorConsulta());
  }

  /**
   * Calcula el costo total del envío.
   * @param envio Contiene los datos relacionados al envio.
   */
  private static void calcularTotal(model.RegistrarEnvio envio) {
    total = 0;
    for (int i = 0; i < envio.getPaquetes().size(); i++) {
      total += envio.getPaquetes().get(i).total;
    }
  }

  /**
   * Calcula el valor en impuestos del envío.
   */
  private static void calcularImpuesto() {
    impuesto = (int) (total * IMPUESTO);
  }

  /**
   * 
   * @return el costo total del envío.
   */
  public static Integer getTotal() {
    return total;
  }

  /**
   * 
   * @return el impuesto del envío.
   */
  public static Integer getImpuesto() {
    return impuesto;
  }
}
