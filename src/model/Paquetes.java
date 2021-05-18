package model;

import org.jooq.impl.DSL;
import utilities.Globals;

public class Paquetes {
  public static class Paquete {
    public static Integer id;
    public static String descripcion;
    public static Integer peso;
    public static Integer id_envio;
  }

  public static void createPaquete(Paquete p) {
    Globals.db().insertInto(DSL.table("paquete"), DSL.field("\"id\""), DSL.field("\"descripcion\""), DSL.field("\"peso\""), DSL.field("\"id_envio\"")).values(p.id, p.descripcion, p.peso, p.id_envio).execute();
    Globals.closeConnection();
  }
}
