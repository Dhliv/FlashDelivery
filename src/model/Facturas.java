package model;

import org.jooq.Result;
import org.jooq.impl.*;
import java.sql.Date;
import org.jooq.Record;
import utilities.Globals;

public class Facturas {
  public static class Factura {
    public static Date Expdate;
    public static String Numeracion;
    public static Integer ID_Envio;
  }

  /**
   * Inserta una nueva factura en la base de datos.
   * 
   * @param expdate    fecha de expedición de la factura.
   * @param numeracion de la factura.
   * @param id_envio   de la factura.
   */
  public static void createFactura(Date expdate, String numeracion, Integer id_envio) {
    Globals.db().insertInto(DSL.table("facturacion"), DSL.field("\"Expdate\""), DSL.field("\"Numeracion\""),
        DSL.field("\"ID_Envio\"")).values(expdate, numeracion, id_envio).execute();
    Globals.closeConnection();
  }
}
