package model;

import org.jooq.impl.*;
import utilities.Conexion;

/**
 * Clase Petición. Contiene parámetros relacionados a la petición de recogida. Posee métodos para la comunicación con la BD.
 * @author David Henao
 * @author Alejandro Pergueza
 * @version 1.0
 * @since 25/09/2021
 */
public class Peticion {
  // private int id;
  // private String descripcion, id_empleado, estado, cliente_solicitud, cliente_destinatario;

  /**
   * Inserta una nueva factura en la base de datos. .
   * 
   * @param costo    del envío
   * @param id_envio de la factura.
   */
  public static void createPeticion(String descripcion, String cedula_empleado, String remitente, String destinatario) {
    // Conexion.db().insertInto(DSL.table("peticion_recogida"), DSL.field("descripcion"), DSL.field("id_empleado"), DSL.field("estado"), DSL.field("cliente_solicitud"), DSL.field("cliente_destinatario"))
    //     .values(descripcion, empleado, "Pendiente", remitente, destinatario).execute();
    String sql = "INSERT INTO peticion_recogida(descripcion, id_empleado, estado, cliente_solicitud, cliente_destinatario) "
               + "VALUES('" + descripcion + "', '" + cedula_empleado + "', 'Pendiente', '" + remitente + "', '" + destinatario + "')";
    Conexion.db().execute(sql);
    Conexion.closeConnection();
  }
}
