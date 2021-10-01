package model;

import java.util.List;

import org.jooq.impl.*;
import utilities.Conexion;

/**
 * Clase Reportes. Posee métodos para la comunicación con la BD con pertinencia
 * a los reportes.
 * 
 * @author David Henao
 * @version 1.0
 * @since 30/09/2021
 */
public class Reportes {

  private String nombre_sede, metodo_pago;
  private int numero_paquetes, veces_usado;

  public Reportes() {
  }

  /**
   * Obtiene el número de paquetes que hay en una sede (enviados o no) y la sede a
   * la que pertenecen.
   * 
   * @return Lista de sedes y su respecitov número de paquetes.
   */
  public static List<Reportes> getNumeroPaquetesBySede() {
    String sql = "select S.nombre as nombre_sede, count(E.id_sede) as numero_paquetes from envio as E inner join sede as S on E.id_sede = S.id group by S.nombre;";
    List<Reportes> numeroPaquetesBySede = Conexion.db().fetch(sql).into(Reportes.class);
    return numeroPaquetesBySede;
  }

  /**
   * Obtiene el número de veces que se ha usado un método de pago en específico.
   * 
   * @return Lista de métodos de pago y las veces que se ha usado.
   */
  public static List<Reportes> getFrecuenciaMetodoPago() {
    String sql = "select metodo_pago, count(metodo_pago) as veces_usado from envio group by metodo_pago;";
    List<Reportes> frecuenciaMetodoPago = Conexion.db().fetch(sql).into(Reportes.class);
    return frecuenciaMetodoPago;
  }
}
