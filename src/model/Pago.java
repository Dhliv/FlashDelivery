package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import controller.OperadorConsulta;
import utilities.Globals;

public class Pago {
  private static Integer total; // Almacena el costo total del envío.
  private static Integer impuesto; // Almacena el impuesto del envío.
  private static Integer seguro; // Almacena el valor del seguro del envío.
  private static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  private static final double SEGURO = 0.06; // porcentaje de seguro.
  private static final double VALOR = 0.01; // porcentaje de valor de paquete.

  /**
   * Inicializa los valores del total e impuesto del envio.
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void initialize(RegistrarEnvio envio) {
    calcularTotal(envio);
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
    seguro = 0;
    double c = 0.0;
    double s = 0.0;
    Integer costo = 0;
    List<model.RegistrarEnvio.Paquete> p = envio.getPaquetes();

    for (int i = 0; i < p.size(); i++) {
      if (p.get(i).seguro) {
        s += (p.get(i).valor_declarado * SEGURO);
      }
      c += ((p.get(i).peso * 5000 + p.get(i).volumen.volumen() * 100) * VALOR);
    }

    seguro = (int) s;
    costo = (int) c;
    calcularImpuesto(costo);
    total = impuesto + costo + seguro;
  }

  /**
   * Calcula el valor en impuestos del envío.
   */
  private static void calcularImpuesto(Integer costo) {
    impuesto = (int) (costo * IMPUESTO);
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

  /**
   * 
   * @return el costo de seguro del envío.
   */
  public static Integer getSeguro() {
    return seguro;
  }
}
