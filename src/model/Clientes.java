package model;

import org.jooq.Result;
import org.jooq.impl.*;
import org.jooq.Record;
import utilities.Globals;

public class Clientes {
  public static class Cliente {
    public static String Cedula;
    public static String Direccion;
    public static String Telefono;
    public static String Nombre;
  }

  public static void createCliente(String cedula, String nombre, String direccion, String telefono) {
    Globals.db().insertInto(DSL.table("cliente"), DSL.field("\"Cedula\""), DSL.field("\"Direccion\""),
        DSL.field("\"Telefono\""), DSL.field("\"Nombre\"")).values(cedula, direccion, telefono, nombre).execute();
    Globals.closeConnection();
  }

  public static boolean clientExists(String cedula) {
    String sql = "select * from cliente where \"Cedula\"='" + cedula + "'";
    Result<Record> rs = Globals.db().fetch(sql);
    Globals.closeConnection();

    if (rs.size() == 1)
      return true;
    return false;
  }
}
