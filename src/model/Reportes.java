package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
  private int numero_paquetes, veces_usado, peticiones_recogida;
  private Double total_sede;
  private final static int MESES = 0;
  private final static int SEMANAS = 1;

  public Reportes() {
  }

  /**
   * Obtiene el número de paquetes que hay en una sede (enviados o no) y la sede a
   * la que pertenecen. (Overview)
   * 
   * @return Lista de sedes y su respectivos número de paquetes.
   */
  public static Number[] getNumeroPaquetesBySede(int id_sede) {
    Number[] data = new Number[6];
    String sql = "select S.nombre as nombre_sede, count(E.id_sede) as numero_paquetes from envio as E inner join sede as S on E.id_sede = S.id where E.id_sede = "
        + id_sede + " and ";

    String aux;
    LocalDate present = LocalDate.now();
    for (int i = 0; i < 6; ++i) {
      aux = sql + " (E.fecha_registro <= '" + present.toString() + "' and E.fecha_registro >'"
          + present.minusMonths(1).toString() + "') group by S.nombre";
      present = present.minusMonths(1);
      List<Reportes> list = Conexion.db().fetch(aux).into(Reportes.class);
      data[i] = (!list.isEmpty() ? list.get(0).numero_paquetes : 0);
    }

    return data;
  }

  // TODO no sabemos exactamente como armar esta query, pues por sede existen 3
  // métodos de pago.
  /**
   * Obtiene el número de veces que se ha usado un método de pago en específico.
   * 
   * @return Lista de métodos de pago y las veces que se ha usado.
   */
  public static Number[] getFrecuenciaMetodoPago(int id_sede) {
    Number[] data = new Number[6];

    // LocalDate present = LocalDate.now();
    // String aux;
    // String sql = "select metodo_pago, count(metodo_pago) as veces_usado from
    // envio where id_sede = " + id_sede + " and";

    // for (int i = 0; i < 6; ++i) {
    // aux = sql + " (fecha_registro <= '" + present.toString() + "' and
    // fecha_registro >'"
    // + (mensual ? present.minusMonths(1).toString() :
    // present.minusWeeks(1)).toString()
    // + "') group by metodo_pago;";
    // present = mensual ? present.minusMonths(1) : present.minusWeeks(1);
    // List<Reportes> list = Conexion.db().fetch(aux).into(Reportes.class);
    // data[i] = (!list.isEmpty() ? list.get(0).veces_usado : 0);
    // }
    return data;
  }

  /**
   * Obtiene una array de Number con las ventas en la sede específica para un
   * periodo de tiempo de 6 semanas o 6 meses sugún se desee. El tamaño del array
   * es siempre 6.
   * 
   * @return Array de ventas por sede.
   */
  public static Number[] getVentasBySedeAndSpecificTime(int id_sede, Boolean mensual) {
    Number[] data = new Number[6];
    LocalDate present = LocalDate.now();
    String aux;
    String sql = "select S.nombre as nombre_sede, cast(sum(F.costo) as numeric) "
        + "as total_sede from envio as E inner join facturacion as F on E.id = F.id_envio "
        + "inner join sede as S on S.id = E.id_sede where E.id_sede =" + id_sede + " and";

    for (int i = 0; i < 6; ++i) {
      aux = sql + " (E.fecha_registro <= '" + present.toString() + "' and E.fecha_registro >'"
          + (mensual ? present.minusMonths(1).toString() : present.minusWeeks(1)).toString() + "') group by S.nombre";
      present = mensual ? present.minusMonths(1) : present.minusWeeks(1);
      List<Reportes> list = Conexion.db().fetch(aux).into(Reportes.class);
      data[i] = (!list.isEmpty() ? list.get(0).total_sede : 0);
    }

    return data;
  }

  /**
   * Obtiene una fecha a partir de la otorgada por parámetro que está 1 mes o
   * semana atrás en el tiempo.
   * 
   * @param t       Fecha a restar un periodo de tiempo.
   * @param periodo Indica cuánto tiempo se restará.
   * @return Fecha con el periodo de tiempo indicado sustraido.
   */
  private static LocalDate backTime(LocalDate t, int periodo) {
    if (periodo == MESES)
      return t.minusMonths(1);
    if (periodo == SEMANAS)
      return t.minusWeeks(1);
    return null;
  }

  /**
   * Obtiene el número de peticiones de recogida que se hicieron en una sede
   * específica.
   * 
   * @param id_sede Sede que se consulta.
   * @return Arreglo de Number de una posición donde estará el número de
   *         peticiones de recogida.
   */
  public static Number[] getPeticionesBySedeAndSpecificTime(int id_sede) {
    Number[] data = new Number[1];
    String sql = "select E.id_sede, count(E.id_sede) as peticiones_recogida"
        + " from peticion_recogida as P inner join empleado as E on E.cedula = P.id_empleado where E.id_sede = "
        + id_sede + " group by E.id_sede";

    var qr = Conexion.db().fetch(sql).into(Reportes.class);
    data[0] = (qr.isEmpty() ? 0 : qr.get(0).peticiones_recogida);

    return data;
  }
}
