package model;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import controller.OperadorConsulta;
import utilities.CreatePDF;
import utilities.Globals;

public class Pago {
  private static Integer total; // Almacena el costo total del envío.
  private static Integer impuesto; // Almacena el impuesto del envío.
  private static Integer seguro; // Almacena el valor del seguro del envío.
  private static String[][] numeracion;
  public static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final double SEGURO = 0.06; // porcentaje de seguro.
  public static final int ValorKG = 1000;
  public static final double ValorCM3 = 0.1;

  /**
   * Inicializa los valores del total e impuesto del envio.
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void initialize(RegistrarEnvio envio) throws IOException {
    calcularTotal(envio);
    parseNumeracion(envio);
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

    Integer idEnvio = Envios.createEnvio(DATE, "Efectivo", total, seguro, impuesto, envio.getDestinatario().direccion, SEDE, EMPLEADO, envio.getRemitente().cedula, envio.getDestinatario().cedula);
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
   * Obtiene la información que debería tener la factura.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  private static void parseNumeracion(model.RegistrarEnvio envio) throws IOException {
    List<model.RegistrarEnvio.Paquete> ps = envio.getPaquetes();
    model.RegistrarEnvio.Paquete p;
    numeracion = new String[ps.size() + 1][2];
    numeracion[0][0] = "Descripción";
    numeracion[0][1] = "Valor";

    for (int i = 0; i < ps.size(); i++) {
      p = ps.get(i);
      numeracion[i + 1][0] = p.descripcion;
      numeracion[i + 1][1] = String.valueOf((int) (p.peso * ValorKG + p.volumen.volumen() * ValorCM3));
    }

    CreatePDF pdf = new CreatePDF(numeracion);
    pdf.pdfCreate();
  }


  /**
   * Calcula el impuesto de un envío.
   * @param costo Subtotal del envio.
   * @return Impuesto correspondiente a pagar.
   */
  private static double calcularImpuesto(double costo){
    return (costo * IMPUESTO);
  }

  /**
   * Calcula el valor del seguro de un envío con base en los paquetes registrados.
   * @param p Datos relacionados a los paquetes del envio.
   * @return Valor del seguro a pagar.
   */
  private static double calcularSeguro(List<model.RegistrarEnvio.Paquete> p){
    double seguro = 0; //Valor del seguro a pagar por el paquete
    for (int i = 0; i < p.size(); i++) {
      if (p.get(i).seguro) {
        seguro += (p.get(i).valor_declarado * SEGURO);
      }
    }
    return seguro;
  }

  /**
   * Calcula el costo de un envío con base en los paquetes registrados.
   * @param p Datos relacionados a los paquetes del envio.
   * @return Subtotal a pagar en el envío.
   */
  private static double calcularCosto(List<model.RegistrarEnvio.Paquete> p){
    double costo = 0;
    for (int i = 0; i < p.size(); i++) {
      costo += (p.get(i).peso * ValorKG + p.get(i).volumen.volumen() * ValorCM3);
    }

    return costo;
  }



  /**
   * Calcula el costo total del envío.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  private static void calcularTotal(model.RegistrarEnvio envio) {
    List<model.RegistrarEnvio.Paquete> p = envio.getPaquetes();
    costo = calcularCosto(p);

    total = (int)(calcularImpuesto(costo) + costo + calcularSeguro(p));
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
