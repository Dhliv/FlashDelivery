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
  public static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final double SEGURO = 0.06; // porcentaje de seguro.
  public static final int ValorKG = 1000;
  public static final int ValorCM3 = 10;

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
   * 
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
      c += (p.get(i).peso * ValorKG + p.get(i).volumen.volumen() * ValorCM3);
    }

    seguro = (int) s;
    costo = (int) c;
    impuesto = (int) (costo * IMPUESTO);
    total = impuesto + costo + seguro;
  }

  public static Integer getTotal() {
    return total;
  }

  public static Integer getImpuesto() {
    return impuesto;
  }

  public static Integer getSeguro() {
    return seguro;
  }
}