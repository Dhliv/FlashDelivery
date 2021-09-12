package model;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import controller.operador.OperadorConsulta;
import model.Entities.Paquete;
import model.Entities.Cliente;
import model.Entities.Empleado;
import utilities.CreatePDF;
import utilities.Globals;

public class Pago {
  private Integer total; // Almacena el costo total del envío.
  private Integer subTotal; // Almacena el subtotal del envío.
  private Integer impuesto; // Almacena el impuesto del envío.
  private Integer seguro; // Almacena el valor del seguro del envío.
  private String[][] numeracion; // Almacena la información organizada sobre los paquetes (valor y descripción).
  private Date date; // Almacena la fecha actual en la que se ejecuta el pago.
  private Integer SEDE; // Almacena la sede a la que pertenece el empleado que registra el pago (envío).
  private String EMPLEADO; // Almacena el número de cedula del empleado que registra el pago (envío).
  private Empleado operador; // Almacena el objeto Empleado del operador de oficina que registra el pago
                             // (envío).
  public static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final double SEGURO = 0.06; // porcentaje de seguro.
  public static final int ValorKG = 1000;
  public static final double ValorCM3 = 0.1;

  /**
   * Inicializa los valores del total e impuesto del envio.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public Pago(RegistrarEnvio envio, Empleado op) throws IOException {
    operador = op;
    SEDE = op.getSede();
    EMPLEADO = op.getCedula();
    date = Date.valueOf(LocalDate.now());

    calcularTotal(envio);
    CreatePDF pdf = new CreatePDF(parsePaquetes(envio), parseCliente(envio.getRemitente()),
        parseCliente(envio.getDestinatario()), parsePago());

    pdf.pdfCreate(Integer.toString(getIdEnvio(envio)));
  }

  /**
   * Inserta toda la información relacionada al pago en un arrau de string para
   * ordenar esta info en la factura.
   * 
   * @return Array con la info organizada.
   */
  public String[] parsePago() {
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
   * @param envio Contiene los datos relacionados al envio.
   */
  public int getIdEnvio(RegistrarEnvio envio) {
    Integer idEnvio = Envio.createEnvio(date, "Efectivo", total, seguro, impuesto, envio.getDestinatario().direccion,
        SEDE, EMPLEADO, envio.getRemitente().cedula, envio.getDestinatario().cedula);
    return idEnvio;
  }

  /**
   * Ingresa los datos pertinentes a la base de datos, como los clientes, los
   * paquetes y el envío.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public void ejecutarPago(RegistrarEnvio envio) {
    Paquete.createPaquetes(envio.getPaquetes(), getIdEnvio(envio));
    goBack();
  }

  /**
   * Vuelve a la pantalla principal del Operador de Oficina.
   */
  private void goBack() {
    Globals.clearViews();
    Globals.cambiarVista("operadorOficinaTabla", new OperadorConsulta(operador));
  }

  /**
   * Almacena toda la información relacionada al cliente en un array de string
   * para organizarla posteriormente en la factura.
   * 
   * @param cliente Objeto cliente con la información pertinente a él.
   * @return Array con la info del cliente organizada.
   */
  private String[] parseCliente(Cliente cliente) {

    String[] numeracionCliente = new String[5];
    numeracionCliente[0] = cliente.nombre;
    numeracionCliente[1] = cliente.cedula;
    numeracionCliente[2] = cliente.ciudad;
    numeracionCliente[3] = cliente.direccion;
    numeracionCliente[4] = cliente.telefono;
    return numeracionCliente;
  }

  /**
   * Calcula el valor de un paquete mediante su masa y su volumen.
   * 
   * @param p Paquete al que se le calcula su costo según su masa y volumen.
   * @return Costo de enviar el paquete considerando solo su masa y volumen.
   */
  private int calcularCostoPaquete(Paquete p) {
    return (int) (p.peso * ValorKG + p.volumen.volumen() * ValorCM3);
  }

  /**
   * Obtiene la información de los paquetes que se debe mostrar en la factura.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  private String[][] parsePaquetes(model.RegistrarEnvio envio) throws IOException {
    List<Paquete> ps = envio.getPaquetes();
    Paquete p;
    numeracion = new String[ps.size() + 1][2];
    numeracion[0][0] = "Descripción";
    numeracion[0][1] = "Valor";

    for (int i = 0; i < ps.size(); i++) {
      p = ps.get(i);
      numeracion[i + 1][0] = p.descripcion;
      numeracion[i + 1][1] = String.valueOf(calcularCostoPaquete(p));
    }

    return numeracion;
  }

  /**
   * Calcula el impuesto de un envío.
   * 
   * @param costo Subtotal del envio.
   * @return Impuesto correspondiente a pagar.
   */
  private double calcularImpuesto(double costo) {
    return (costo * IMPUESTO);
  }

  /**
   * Calcula el valor del seguro de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Valor del seguro a pagar.
   */
  private double calcularSeguro(List<Paquete> p) {
    double seguro = 0; // Valor del seguro a pagar por el paquete
    for (int i = 0; i < p.size(); i++) {
      if (p.get(i).seguro) {
        seguro += (p.get(i).valor * SEGURO);
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
  private double calcularCosto(List<Paquete> p) {
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
  private void calcularTotal(model.RegistrarEnvio envio) {
    List<Paquete> p = envio.getPaquetes();

    subTotal = (int) calcularCosto(p);
    impuesto = (int) calcularImpuesto(subTotal);
    seguro = (int) calcularSeguro(p);

    total = subTotal + impuesto + seguro;
  }

  public Integer getTotal() {
    return total;
  }

  public Integer getSubTotal() {
    return subTotal;
  }

  public Integer getImpuesto() {
    return impuesto;
  }

  public Integer getSeguro() {
    return seguro;
  }

  public Date getDate() {
    return date;
  }
}
