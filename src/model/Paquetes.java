package model;

import org.jooq.Result;
import org.jooq.impl.*;
import org.jooq.Record;
import utilities.Globals;

public class Paquetes {
  public static class Paquete {
    public static String Descripcion;
    public static Double Peso;
    public static Double valor;
    public static Integer ID_Envio;
  }

  /**
   * Inserta un nuevo paquete en la base de datos.
   * 
   * @param peso        del paquete.
   * @param valor       del paquete.
   * @param id_envio    del paquete.
   * @param descripcion del paquete.
   */
  public static void createPaquete(Double peso, Double valor, Integer id_envio, String descripcion) {
    Globals.db().insertInto(DSL.table("paquete"), DSL.field("\"Peso\""), DSL.field("\"Valor\""),
        DSL.field("\"ID_Envio\""), DSL.field("\"Descripcion\"")).values(peso, valor, id_envio, descripcion).execute();
    Globals.closeConnection();
  }
}
