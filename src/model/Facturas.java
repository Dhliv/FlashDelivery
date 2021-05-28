package model;

import org.jooq.Result;
import org.jooq.impl.*;
import java.sql.Date;
import org.jooq.Record;
import utilities.Globals;

public class Facturas {
  public class Factura {
    public Integer id_factura;
    public Date expdate;
    public String numeracion;
    public Integer id_envio;
  }

  /**
   * Inserta una nueva factura en la base de datos.
   * 
   * @param expdate    fecha de expedición de la factura.
   * @param numeracion de la factura.
   * @param id_envio   de la factura.
   */
  public static void createFactura(Date expdate, String numeracion, Integer id_envio) {
    Globals.db().insertInto(DSL.table("facturacion"), DSL.field("expdate"), DSL.field("numeracion"), DSL.field("id_envio")).values(expdate, numeracion, id_envio).execute();
    Globals.closeConnection();
  }
}
