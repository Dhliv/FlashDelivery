package model.Entities;

import org.jooq.impl.*;
import org.jooq.Record;
import utilities.Conexion;

/**
 * POJO y funcionalidades sobre la entidad cliente
 * 
 * @author Julián Orejuela
 * @version 1.1, 1/9/2021
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
   */
  public static void createCliente(String cedula, String nombre, String direccion, String telefono) {
    Conexion.db().insertInto(DSL.table("cliente"), DSL.field("\"Cedula\""), DSL.field("\"Direccion\""),
        DSL.field("\"Telefono\""), DSL.field("\"Nombre\"")).values(cedula, direccion, telefono, nombre).execute();
    Conexion.closeConnection();
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
    Conexion.closeConnection();
    return cliente;
  }
}
