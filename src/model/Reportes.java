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
  private int numero_paquetes, veces_usado, peticiones_recogida, clientes;
  private Double total_sede;
  private final static int MESES = 0;
  private final static int SEMANAS = 1;
  private final static int SIZEINTERVALO = 6;

  public Reportes() {
  }

  /**
   * Obtiene el número de paquetes que hay en una sede (enviados o no) y la sede a
   * la que pertenecen. (Overview)
   * 
   * @return Lista de sedes y su respectivos número de paquetes.
   */
  public static Number[] getNumeroPaquetesBySede(int id_sede) {
    Number[] data = new Number[SIZEINTERVALO];
    String sql = "select S.nombre as nombre_sede, count(E.id_sede) as numero_paquetes from envio as E inner join sede as S on E.id_sede = S.id where E.id_sede = "
        + id_sede + " and ";

    String aux;
    LocalDate present = LocalDate.now();
    for (int i = 0; i < SIZEINTERVALO; ++i) {
      aux = sql + " (E.fecha_registro <= '" + present.toString() + "' and E.fecha_registro >'"
          + present.minusMonths(1).toString() + "') group by S.nombre";
      present = present.minusMonths(1);
      List<Reportes> list = Conexion.db().fetch(aux).into(Reportes.class);
      data[i] = (!list.isEmpty() ? list.get(0).numero_paquetes : 0);
    }

    return data;
  }

  /**
   * Obtiene el número de veces que se ha usado un método de pago en específico.
   * 
   * @return Lista de métodos de pago y las veces que se ha usado.
   */
  public static Number[] getFrecuenciaMetodoPago(int id_sede, String medioPago) {
    Number[] data = new Number[6];

    LocalDate present = LocalDate.now();
    String aux;
    String sql = "select metodo_pago, count(metodo_pago) as veces_usado from envio where id_sede = " + id_sede
        + " and metodo_pago = '" + medioPago + "' and";

    for (int i = 0; i < 6; ++i) {
      aux = sql + " (fecha_registro <= '" + present.toString() + "' and fecha_registro >'"
          + present.minusMonths(1).toString() + "') group by metodo_pago;";
      present = present.minusMonths(1);
      List<Reportes> list = Conexion.db().fetch(aux).into(Reportes.class);
      data[i] = (!list.isEmpty() ? list.get(0).veces_usado : 0);
    }
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
    Number[] data = new Number[SIZEINTERVALO];
    LocalDate present = LocalDate.now();
    int periodo = mensual ? MESES : SEMANAS;
    String aux;
    String sql = "select S.nombre as nombre_sede, cast(sum(F.costo) as numeric) "
        + "as total_sede from envio as E inner join facturacion as F on E.id = F.id_envio "
        + "inner join sede as S on S.id = E.id_sede where E.id_sede =" + id_sede + " and";

    for (int i = 0; i < SIZEINTERVALO; ++i) {
      aux = sql + " (E.fecha_registro <= '" + present.toString() + "' and E.fecha_registro >'"
          + backTime(present, periodo).toString() + "') group by S.nombre";
      present = backTime(present, periodo);
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

  /**
   * Obtiene el número de clientes registrados en una sede en un intervalo de 6
   * meses.
   * 
   * @param id_sede La sede a consultar.
   * @return Array Number con el número de clientes registrados en la sede para
   *         cada mes en un intervalo de 6 meses atrás.
   */
  public static Number[] getClientesBySedeAndSpecificTime(int id_sede) {
    Number[] data = new Number[SIZEINTERVALO];
    String aux, sql;
    LocalDate present = LocalDate.now();
    int periodo = MESES;
    sql = "select count(rem) as clientes from "
        + "(select distinct rem, (first_value(fr) over ( partition by rem order by fr)) as fr, (first_value (se) over ( partition by rem order by fr)) as se "
        + "from ((select * from " + "(select distinct e.cliente_envio as rem, "
        + "(first_value(e.fecha_registro) over ( partition by e.cliente_envio order by fecha_registro)) as fr, "
        + "(first_value (e.id_sede) over ( partition by e.cliente_envio order by fecha_registro)) as se from envio e) as sb"
        + ") UNION (select * from " + "(select distinct e.cliente_entrega as rem, "
        + "(first_value(e.fecha_registro) over ( partition by e.cliente_entrega order by fecha_registro)) as fr, "
        + "(first_value (e.id_sede) over ( partition by e.cliente_entrega order by fecha_registro)) as se from envio e) as sb "
        + ")) as sb2 where ";

    for (int i = 0; i < SIZEINTERVALO; i++) {
      aux = sql + "fr > '" + backTime(present, periodo).toString() + "' and fr <= '" + present.toString()
          + "' and se = " + id_sede + ") as sb3;";
      present = backTime(present, periodo);
      List<Reportes> lista = Conexion.db().fetch(aux).into(Reportes.class);
      data[i] = (lista.isEmpty() ? 0 : lista.get(0).clientes);
    }

    return data;
  }
}
