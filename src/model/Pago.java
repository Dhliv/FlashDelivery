package model;

import java.io.IOException;
import java.time.LocalDate;

import model.Entities.Paquete;
import model.Entities.Cliente;
import model.Entities.Empleado;
import utilities.CreatePDF;

/**
 * Clase Pago. Contiene los atributos relacionados al pago de un envío, ademas de los métodos e
 * interacciones necesarias para la inserción y comunicación con la BD. También es el proxy para la
 * generación del pdf de la factura.
 * 
 * @author David Henao
 * @version 1.1
 * @since 24/09/2021
 */
public class Pago {
  private Double total; // Almacena el costo total del envío.
  private Double subTotal; // Almacena el subtotal del envío.
  private Double impuesto; // Almacena el impuesto del envío.
  private Double seguro; // Almacena el valor del seguro del envío.
  private String[][] numeracion; // Almacena la información organizada sobre los paquetes (valor y descripción).
  private Empleado operador; // Almacena el objeto Empleado del operador de oficina que registra el pago
                             // (envío).
  private Integer id_envio; // Almacena el id del envío que se registra en la BD.
  public static final Double IMPUESTO = 0.19; // porcentaje de impuesto.
  public static final Double SEGURO = 0.06; // porcentaje de seguro.
  public static final long ValorKG = 1000;
  public static final Double ValorCM3 = 0.1;
  public static final Double SEGUROMINIMO = 7000.0; // Valor mínimo a cobrar por el seguro de un paquete.

  /**
   * Inicializa los valores del total, impuesto y seguro del envio.
   * 
   * @param envio Contiene los datos relacionados al envio.
   */
  public Pago(RegistrarEnvio envio, Empleado op) throws IOException {
    operador = op;
    calcularTotal(envio);
  }

  /**
   * Inserta toda la información relacionada al pago en un array de string para ordenar esta info en
   * la factura.
   * 
   * @return Array con la info organizada.
   */
  public String[] parsePago() {
    String[] pago = new String[5];
    pago[0] = LocalDate.now().toString();
    pago[1] = Double.toString(getTotal());
    pago[2] = Double.toString(getImpuesto());
    pago[3] = Double.toString(getSeguro());
    pago[4] = Double.toString(getSubTotal());
    return pago;
  }

  /**
   * Ingresa los datos pertinentes a la base de datos, como los clientes, los paquetesel e, nvío y la
   * factura. Adicionalmente se procede a generar un pdf de la factura.
   * 
   * @param envio       Contiene los datos relacionados al envio.
   * @param metodo_pago Especifica el metodo de pago usado.
   */
  public void ejecutarPago(RegistrarEnvio envio, String metodo_pago) {

    // TODO implementar los siguientes métodos en hilos:
    Cliente.createCliente(envio.getRemitente());
    Cliente.createCliente(envio.getDestinatario());
    id_envio = Envio.createEnvio(envio, metodo_pago, operador);
    Paquete.createPaquete(envio.getPaquete(), id_envio);
    Facturas.createFactura(getTotal(), id_envio);

    CreatePDF pdf = new CreatePDF(parsePaquete(envio), parseCliente(envio.getRemitente()), parseCliente(envio.getDestinatario()), parsePago());
    try {
      pdf.pdfCreate(Integer.toString(id_envio));
    } catch (IOException e) {
      // e.printStackTrace();
    }
  }

  /**
   * Almacena toda la información relacionada al cliente en un array de string para organizarla
   * posteriormente en la factura.
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
  private Double calcularImpuesto(Double costo) {
    return (costo * IMPUESTO);
  }

  /**
   * Calcula el valor del seguro de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Valor del seguro a pagar.
   */
  private Double calcularSeguro(Paquete p) {
    Double seguro = 0.0; // Valor del seguro a pagar por el paquete
    if (p.seguro) seguro = (p.valor * SEGURO);
    return seguro;
  }

  /**
   * Calcula el costo de un envío con base en los paquetes registrados.
   * 
   * @param p Datos relacionados a los paquetes del envio.
   * @return Subtotal a pagar en el envío.
   */
  private Double calcularCosto(Paquete p) {
    Double costo = 0.0;
    costo = (p.peso * ValorKG + p.getVolumen() * ValorCM3);

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

  public Double getTotal() {
    return total;
  }

  public Double getSubTotal() {
    return subTotal;
  }

  public Double getImpuesto() {
    return impuesto;
  }

  public Double getSeguro() {
    return (seguro > SEGUROMINIMO ? seguro : SEGUROMINIMO);
  }
}
