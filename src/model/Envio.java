package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import model.Entities.Empleado;
import utilities.Conexion;

/**
 * 
 * @author David Henao
 * @version 1.1 24/09/2021
 */
public class Envio {
  public Integer id;
  public Date fecha_registro;
  public String metodo_pago;
  public String direccion_entrega;
  public Integer id_sede;
  public String emp_entrega;
  public Boolean delivered;
  public String cliente_envio;
  public String cliente_entrega;

  public Integer getIdenvio() {
    return id;
  }

  public Integer getIdsede() {
    return id_sede;
  }

  public Boolean isDelivered() {
    return delivered;
  }

  public Date getFecha_registro() {
    return fecha_registro;
  }

  public String getDireccion_entrega() {
    return direccion_entrega;
  }

  public String getMetodo_pago() {
    return metodo_pago;
  }

  public String getCliente_envio() {
    return cliente_envio;
  }

  public String getCliente_entrega() {
    return cliente_entrega;
  }

  /**
   * Inserta un envío en la base de datos con sus
   * respectivos parametros. Inserta un envío en la base de datos con sus
   * respectivos parametros, y retorna el id del envío recién insertado.
   * 
   * @param fecha_registro
   * @param metodo_pago
   * @param costo
   * @param seguro
   * @param impuesto_envio
   * @param direccion_entrega
   * @param id_sede
   * @param emp_entrega
   * @param cliente_envio
   * @param cliente_entrega
   * @return El ID del envío.
   */
  public static Integer createEnvio(Date fecha_registro, String metodo_pago, String direccion_entrega, Integer id_sede,
      String emp_entrega, String cliente_envio, String cliente_entrega) {
    String sql = "insert into envio(fecha_registro, metodo_pago, direccion_entrega, id_sede, empleado_entrega, delivered, cliente_envio, cliente_entrega) values('"
        + fecha_registro + "', '" + metodo_pago + "', '" + direccion_entrega + "', " + id_sede + ", '" + emp_entrega
        + "', FALSE, '" + cliente_envio + "', '" + cliente_entrega + "') returning id";
    var query = Conexion.db().fetchOne(sql);
    Conexion.closeConnection();

    return Integer.parseInt(query.getValue(0).toString());
  }

  /**
   * Inserta un nuevo envío en la base de datos.
   * 
   * @param re Envío a insertar.
   * @return El ID del envío insertado.
   */
  public static Integer createEnvio(RegistrarEnvio re, String metodo_pago, Empleado operador) {
    Envio e = new Envio();
    e.cliente_entrega = re.getDestinatario().cedula;
    e.cliente_envio = re.getRemitente().cedula;
    e.fecha_registro = Date.valueOf(LocalDate.now());
    e.metodo_pago = metodo_pago;
    e.direccion_entrega = re.getDestinatario().direccion;
    e.id_sede = operador.getSede();
    e.emp_entrega = operador.getCedula();

    Integer id_envio = createEnvio(e.fecha_registro, e.metodo_pago, e.direccion_entrega, e.id_sede, e.emp_entrega,
        e.cliente_envio, e.cliente_entrega);
    return id_envio;
  }

  /**
   * Obtiene los envíos existentes en una sede específica.
   * 
   * @param id_sede Sede en la que se consulta.
   * @return Lista de envíos existentes en la sede.
   */
  public static List<Envio> getEnviosBySede(Integer id_sede) {
    List<Envio> envios = Conexion.db().select().from("envio").naturalJoin("paquete").where("id_sede = " + id_sede)
        .fetch().into(Envio.class);
    Conexion.closeConnection();
    return envios;
  }
}
