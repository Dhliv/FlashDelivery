package model.Entities;

import org.jooq.impl.*;
import org.jooq.Record;
import utilities.Conexion;

/**
 * POJO y funcionalidades sobre la entidad cliente
 * 
 * @author Julián Orejuela
 * @author David Henao
 * @version 1.2, 19/9/2021
 */
public class Cliente {
  public String cedula;
  public String nombre;
  public String ciudad;
  public String direccion;
  public String telefono;
  public Boolean existInDB;

  /**
   * Registra a un nuevo cliente en la base de datos.
   * 
   * @param cedula    del cliente.
   * @param nombre    del clliente.
   * @param direccion del cliente.
   * @param telefono  del cliente.
   * @param ciudad    del Cliente
   */
  public static void createCliente(String cedula, String nombre, String direccion, String telefono, String ciudad) {
    Conexion.db().insertInto(DSL.table("cliente"), DSL.field("cedula"), DSL.field("nombre"), DSL.field("ciudad"), DSL.field("direccion"), DSL.field("telefono")).values(cedula, direccion, ciudad, nombre, telefono).execute();
    Conexion.closeConnection();
  }

  /**
   * Interfaz para registrar a un cliente mediante el objeto de Cliente, solo en
   * caso de que ya no existiera.
   * 
   * @param c Cliente a registrar en la BD.
   */
  public static void createCliente(Cliente c) {
    // if (!c.existInDB)
    createCliente(c.cedula, c.nombre, c.direccion, c.telefono, c.ciudad);
  }

  /**
   * Verifica si un cliente en específico existe en la base de datos.
   * 
   * @param cedula del cliente.
   * @return true si el cliente existía, false de lo contrario.
   */
  public static boolean clientExists(String cedula) {
    return buscarCliente(cedula) != null;
  }

  /**
   * Busca un cliente en la base de datos con la cedula suministrada.
   * 
   * @param cedula
   * @return El {@code cliente} o {@code null} si no existe.
   */
  public static Cliente buscarCliente(String cedula) {
    Cliente cliente;
    Record rs = Conexion.db().select().from("cliente").where("cedula='" + cedula + "'").fetchOne();
    cliente = rs != null ? rs.into(Cliente.class) : null;
    // Conexion.closeConnection();
    return cliente;
  }
}
