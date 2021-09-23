package model;

import org.jooq.impl.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import utilities.Conexion;

public class Facturas {
  public class Factura {
    public Integer id_factura;
    public Date expdate;
    public String numeracion;
    public Integer id_envio;
  }

  /**
   * Inserta una nueva factura en la base de datos. .
   * 
   * @param costo    del envío
   * @param id_envio de la factura.
   */
  public static void createFactura(Double costo, Integer id_envio) {

    Date expdate = Date.valueOf(LocalDate.now());
    Conexion.db().insertInto(DSL.table("facturacion"), DSL.field("expdate"), DSL.field("costo"), DSL.field("id_envio"))
        .values(expdate, BigDecimal.valueOf(costo), id_envio).execute();
    Conexion.closeConnection();
  }
}
