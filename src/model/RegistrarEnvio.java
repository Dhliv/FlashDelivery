package model;

import org.jooq.Result;
import org.jooq.impl.*;
import java.sql.Date;
import org.jooq.Record;

import static utilities.Globals.db;
import utilities.Globals;
import model.Clientes.Cliente;

/**
 * Clase encargada de almacenar todos los datos para registrar un envio y
 * posteriormente guardarlos en la base de datos.
 * @author Julián Orejuela
 * @version 1.0, 29/4/2021
 */
public class RegistrarEnvio {
  private Cliente remitente;
  private Cliente destinatario;

  /**
   * Busca si un cliente ya se encuentra registrado en la base de datos.
   * @param cedula
   * @param tipo
   * @return El cliente o {@code null} si no existe.
   */
  public Cliente buscarCliente(String cedula, TipoCliente tipo) {
    Record rs = db().select().from("cliente").where("cedula='" + cedula + "'").fetchOne();
    Cliente cliente = rs != null ? rs.into(Cliente.class) : null;
    if (tipo == TipoCliente.Remitente) remitente = cliente;
    if (tipo == TipoCliente.Destinatario) destinatario = cliente;
    Globals.closeConnection();
    return cliente;
  }

  /**
   * Inserta un nuevo envío en la base de datos.
   * 
   * @param fcreg fecha de registro.
   * @param mp    metodo de pago.
   * @param cost  costo del envio.
   * @param seg   define si el paquete tiene seguro o no.
   * @param impe  valor del impuesto del envío.
   * @param dir   dirección de entrega del paquete.
   * @param idS   ID de la sede donde se registra el envío.
   * @param emp   ID del empleado que registra el envío.
   * @param remi  cédula del cliente que envía el paquete.
   * @param desti cédula del cliente que recibe el paquete.
   */
  public static void createEnvio(Date fcreg, String mp, Double cost, Boolean seg, Double impe, String dir, Integer idS, Integer emp, String remi, String desti) {
    Globals.db()
        .insertInto(DSL.table("envio"), DSL.field("\"Fecha_registro\""), DSL.field("\"Metodo_pago\""), DSL.field("\"Costo\""), DSL.field("\"Seguro\""), DSL.field("\"Impuesto_envio\""), DSL.field("\"Direccion_entrega\""),
            DSL.field("\"ID_Sede\""), DSL.field("\"Emp_Entrega\""), DSL.field("\"Delivered\""), DSL.field("\"Cliente_Envio\""), DSL.field("\"Cliente_Recogida\""))
        .values(fcreg, mp, cost, seg, impe, dir, idS, emp, false, remi, desti).execute();
    Globals.closeConnection();
  }

  // #---------------------------------------------------------------------------
  // # ENUMS
  // #---------------------------------------------------------------------------

  public enum TipoCliente {
    Remitente, Destinatario
  }

  // #---------------------------------------------------------------------------
  // # POJOs
  // #---------------------------------------------------------------------------

  public static class Envio {
    public Integer ID_Envio;
    public Date Fecha_registro;
    public String Metodo_pago;
    public Double Costo;
    public Boolean Seguro;
    public Double Impuesto_envio;
    public String Direccion_entrega;
    public Integer ID_Sede;
    public Integer Emp_Entrega;
    public Boolean Delivered;
    public String Cliente_Envio;
    public String Cliente_Recogida;
  }

  public static class Paquete {
    public String descripcion;
    public Double peso;
    public Double valor;
    public Integer id_envio;
  }
}
