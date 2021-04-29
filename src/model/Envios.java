package model;

import org.jooq.Result;
import org.jooq.impl.*;
import java.sql.Date;
import org.jooq.Record;
import utilities.Globals;

public class Envios {
  public static class Envio {
    public static Integer ID_Envio;
    public static Date Fecha_registro;
    public static String Metodo_pago;
    public static Double Costo;
    public static Boolean Seguro;
    public static Double Impuesto_envio;
    public static String Direccion_entrega;
    public static Integer ID_Sede;
    public static Integer Emp_Entrega;
    public static Boolean Delivered;
    public static String Cliente_Envio;
    public static String Cliente_Recogida;
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
  public static void createEnvio(Date fcreg, String mp, Double cost, Boolean seg, Double impe, String dir, Integer idS,
      Integer emp, String remi, String desti) {
    Globals.db()
        .insertInto(DSL.table("envio"), DSL.field("\"Fecha_registro\""), DSL.field("\"Metodo_pago\""),
            DSL.field("\"Costo\""), DSL.field("\"Seguro\""), DSL.field("\"Impuesto_envio\""),
            DSL.field("\"Direccion_entrega\""), DSL.field("\"ID_Sede\""), DSL.field("\"Emp_Entrega\""),
            DSL.field("\"Delivered\""), DSL.field("\"Cliente_Envio\""), DSL.field("\"Cliente_Recogida\""))
        .values(fcreg, mp, cost, seg, impe, dir, idS, emp, false, remi, desti).execute();
    Globals.closeConnection();
  }
}
