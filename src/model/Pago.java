package model;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import controller.OperadorConsulta;
import model.Entities.Paquetes;
import model.Entities.Clientes.Cliente;
import utilities.CreatePDF;
import utilities.Globals;

// TODO eliminar static de todas las funciones y establecer por constructor.
public class Pago {
  private static Integer total; // Almacena el costo total del envío.
  private static Integer subTotal; // Almacena el subtotal del envío.
  private static Integer impuesto; // Almacena el impuesto del envío.
  private static Integer seguro; // Almacena el valor del seguro del envío.
  private static String[][] numeracion;
  private static Date date;
  private static Integer SEDE;
  private static String EMPLEADO;

  public static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final double SEGURO = 0.06; // porcentaje de seguro.
  public static final int ValorKG = 1000;
  public static final double ValorCM3 = 0.1;

  /**
   * Inicializa los valores del total e impuesto del envio.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void initialize(RegistrarEnvio envio) throws IOException {
    SEDE = Globals.empleado.getSede();
    EMPLEADO = Globals.empleado.getCedula();
    date = Date.valueOf(LocalDate.now()); // Fecha actual.
    calcularTotal(envio);
    date = Date.valueOf(LocalDate.now());
    CreatePDF pdf = new CreatePDF(parsePaquetes(envio), parseCliente(envio.getRemitente()),
        parseCliente(envio.getDestinatario()), parsePago());

    pdf.pdfCreate(Integer.toString(getIdEnvio(envio)));
  }

  /**
   * Retorna la información de pago.
   * 
   * @return String Array con: Fecha, Total, Impuesto, Seguro, Subtotal del envio.
   */
  public static String[] parsePago() {
    String[] pago = new String[5];
    pago[0] = getDate().toString();
    pago[1] = Integer.toString(getTotal());
    pago[2] = Integer.toString(getImpuesto());
    pago[3] = Integer.toString(getSeguro());
    pago[4] = Integer.toString(getSubTotal());
    return pago;
  }

  /**
   * Genera el id del envío.
   * 
   * @param envio Objeto que contiene los datos relacionados al envio.
   */
  public static int getIdEnvio(RegistrarEnvio envio) {
    Integer idEnvio = Envios.createEnvio(date, "Efectivo", total, seguro, impuesto, envio.getDestinatario().direccion,
        SEDE, EMPLEADO, envio.getRemitente().cedula, envio.getDestinatario().cedula);
    return idEnvio;
  }

  /**
   * Ingresa los datos pertinentes a la base de datos: clientes, paquetes y el
   * envío.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public static void ejecutarPago(RegistrarEnvio envio) {
    Paquetes.createPaquetes(envio.getPaquetes(), getIdEnvio(envio));
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
   * Retorna la información de un objeto Cliente en un Array de Strings.
   * 
   * @param cliente
   * @return Array de Strings con la información del cliente: nombre, cedula,
   *         ciudad, dirección, telefono.
   */
  private static String[] parseCliente(Cliente cliente) {

    String[] numeracionCliente = new String[5];
    numeracionCliente[0] = cliente.nombre;
    numeracionCliente[1] = cliente.cedula;
    numeracionCliente[2] = cliente.ciudad;
    numeracionCliente[3] = cliente.direccion;
    numeracionCliente[4] = cliente.telefono;
    return numeracionCliente;
  }

  /**
   * Obtiene la información de los paquetes que se debe mostrar en la factura.
   * 
   * @param envio Contiene los datos relacionados al envio.
   * @return Array de Strings con la descripción y el precio de cada uno de los
   *         paquetes de un envio.
   */
  private static String[][] parsePaquetes(model.RegistrarEnvio envio) throws IOException {
    List<model.RegistrarEnvio.Paquete> ps = envio.getPaquetes();

    model.RegistrarEnvio.Paquete p;
    numeracion = new String[ps.size()][2];

    for (int i = 0; i < ps.size(); i++) {
      p = ps.get(i);
      numeracion[i][0] = p.descripcion;
      numeracion[i][1] = String.valueOf((int) (p.peso * ValorKG + p.volumen.volumen() * ValorCM3));
    }

    return numeracion;
  }

  /**
   * Calcula el impuesto de un envío.
   * 
   * @param costo Subtotal del envio.
   * @return Impuesto correspondiente a pagar.
   */
  private static double calcularImpuesto(double costo) {
    return (costo * IMPUESTO);
  }

  /**
   * Calcula el valor del seguro de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Valor del seguro a pagar.
   */
  private static double calcularSeguro(List<model.RegistrarEnvio.Paquete> p) {
    double seguro = 0; // Valor del seguro a pagar por el paquete
    for (int i = 0; i < p.size(); i++) {
      if (p.get(i).seguro) {
        seguro += (p.get(i).valor_declarado * SEGURO);
      }
    }
    return seguro;
  }

  /**
   * Calcula el costo de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Subtotal a pagar en el envío.
   */
  private static double calcularCosto(List<model.RegistrarEnvio.Paquete> p) {
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

    subTotal = (int) calcularCosto(p);
    impuesto = (int) calcularImpuesto(subTotal);
    seguro = (int) calcularSeguro(p);

    total = subTotal + impuesto + seguro;
  }

  public static Integer getTotal() {
    return total;
  }

  public static Integer getSubTotal() {
    return subTotal;
  }

  public static Integer getImpuesto() {
    return impuesto;
  }

  public static Integer getSeguro() {
    return seguro;
  }

  public static Date getDate() {
    return date;
  }
}
