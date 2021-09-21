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

/**
 * @author David Henao
 * @version 1.0 19/9/2021
 */
public class Pago {
  private Integer total; // Almacena el costo total del envío.
  private Integer subTotal; // Almacena el subtotal del envío.
  private Integer impuesto; // Almacena el impuesto del envío.
  private Integer seguro; // Almacena el valor del seguro del envío.
  private String[][] numeracion; // Almacena la información organizada sobre los paquetes (valor y descripción).
  private Date date; // Almacena la fecha actual en la que se ejecuta el pago.
  private Empleado operador; // Almacena el objeto Empleado del operador de oficina que registra el pago
                             // (envío).
  private Integer id_envio; // Almacena el id del envío que se registra en la BD.
  public static final double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final double SEGURO = 0.06; // porcentaje de seguro.
  public static final int ValorKG = 1000;
  public static final double ValorCM3 = 0.1;

  /**
   * Inicializa los valores del total, impuesto y seguro del envio.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public Pago(RegistrarEnvio envio, Empleado op) throws IOException {
    operador = op;
    date = Date.valueOf(LocalDate.now());

    calcularTotal(envio);
  }

  /**
   * Inserta toda la información relacionada al pago en un array de string para
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
   * Ingresa los datos pertinentes a la base de datos, como los clientes, los
   * paquetes y el envío. Adicionalmente se procede a generar la factura.
   * 
   * @param envio       Contiene los datos relacionados al envio.
   * @param metodo_pago Especifica el metodo de pago usado.
   */
  public void ejecutarPago(RegistrarEnvio envio, String metodo_pago) {
    Cliente.createCliente(envio.getDestinatario());
    Cliente.createCliente(envio.getRemitente());
    id_envio = Envio.createEnvio(envio, metodo_pago, operador);
    Paquete.createPaquete(envio.getPaquete());

    CreatePDF pdf = new CreatePDF(parsePaquete(envio), parseCliente(envio.getRemitente()),
        parseCliente(envio.getDestinatario()), parsePago());
    try {
      // TODO Cambiar implementación de pdfCreate.
      pdf.pdfCreate(Integer.toString(id_envio));
    } catch (IOException e) {
      
      e.printStackTrace();
    }
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
   * Obtiene la información del paquete que se debe mostrar en la factura.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  private String[][] parsePaquete(model.RegistrarEnvio envio) {
    Paquete p = envio.getPaquete();
    numeracion = new String[1][2];
    numeracion[0][0] = p.descripcion;
    numeracion[0][1] = String.valueOf(calcularCosto(p));

    return numeracion;
  }

  /**
   * Calcula el impuesto de un envío.
   * 
   * @param costo Subtotal del envio.
   * @return Impuesto correspondiente a pagar.
   */
  private int calcularImpuesto(int costo) {
    return (int) (costo * IMPUESTO);
  }

  /**
   * Calcula el valor del seguro de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Valor del seguro a pagar.
   */
  private int calcularSeguro(Paquete p) {
    int seguro = 0; // Valor del seguro a pagar por el paquete
    if (p.seguro)
      seguro = (int) (p.valor * SEGURO);
    return seguro;
  }

  /**
   * Calcula el costo de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Subtotal a pagar en el envío.
   */
  private int calcularCosto(Paquete p) {
    int costo = 0;
    costo = (int) (p.peso * ValorKG + p.getVolumen() * ValorCM3);

    return costo;
  }

  /**
   * Calcula el costo total del envío.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  private void calcularTotal(model.RegistrarEnvio envio) {
    Paquete p = envio.getPaquete();

    subTotal = calcularCosto(p);
    impuesto = calcularImpuesto(subTotal);
    seguro = calcularSeguro(p);

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
