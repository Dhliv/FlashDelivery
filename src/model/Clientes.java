package model;

import org.jooq.Result;
import org.jooq.impl.*;
import org.jooq.Record;
import utilities.Globals;

public class Clientes {
  public static class Cliente {
    public String cedula;
    public String direccion;
    public String telefono;
    public String nombre;
  }

  /**
   * Registra a un nuevo cliente en la base de datos.
   * 
   * @param cedula    del cliente.
   * @param nombre    del clliente.
   * @param direccion del cliente.
   * @param telefono  del cliente.
   */
  public static void createCliente(String cedula, String nombre, String direccion, String telefono) {
    Globals.db().insertInto(DSL.table("cliente"), DSL.field("\"Cedula\""), DSL.field("\"Direccion\""),
        DSL.field("\"Telefono\""), DSL.field("\"Nombre\"")).values(cedula, direccion, telefono, nombre).execute();
    Globals.closeConnection();
  }

  /**
   * Verifica si un cliente en específico existe en la base de datos.
   * 
   * @param cedula del cliente.
   * @return true si el cliente existía, false de lo contrario.
   */
  public static boolean clientExists(String cedula) {
    String sql = "select * from cliente where \"Cedula\"='" + cedula + "'";
    Result<Record> rs = Globals.db().fetch(sql);
    Globals.closeConnection();

    if (rs.size() == 1) // Si la query retorna un resultado, el cliente existe.
      return true;
    return false;
  }
}
