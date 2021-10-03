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
  private int numero_paquetes, veces_usado;
  private Double total_sede;

  public Reportes() {
  }

  /**
   * Obtiene el número de paquetes que hay en una sede (enviados o no) y la sede a
   * la que pertenecen. (Overview)
   * 
   * @return Lista de sedes y su respectivos número de paquetes.
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

  /**
   * Obtiene una lista de ventas por sede para un periodo de tiempo espicificado.
   * El tamaño de la lista es el número de dias que hay desde el inicio hasta al
   * final del intervalo de tiempo.
   * 
   * @param inicio Inicio del intervalo de tiempo a consultar.
   * @param end    Fin del intervalo de tiempo a consultar.
   * @return Lista de ventas por sede.
   */
  public static List<Reportes> getVentasBySedeAndSpecificTime(LocalDate inicio, LocalDate end) {
    String sql = "select S.nombre as nombre_sede, cast(sum(F.costo) as numeric) as total_sede from envio as E inner join facturacion as F on E.id = F.id_envio inner join sede as S on S.id = E.id_sede where E.fecha_registro = '"
        + inicio.toString() + "' group by S.nombre";
    List<Reportes> ventasBySede = Conexion.db().fetch(sql).into(Reportes.class);
    return ventasBySede;
  }

  // TODO @WINJA REALIZAR ESTA Y LAS DEMÁS QUERIES SOLICITADAS.

  /**
   * Retorna los medios de pago usados por la sede
   * 
   * @param id_sede El id de la sede.
   * @return
   */
  public static Number[] getMedioDePago(int id_sede) {
    Number[] prueba = new Number[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }; // Solo para efectos practicos.
    ArrayList<Number> datos = new ArrayList<Number>();

    // TODO @REYJALL @WINNELL @ARKADIA
    // ! VAN EN MESES LAS FECHAS QUE NECESITO A MENOS DE QUE SE REQUIERA
    // EXPLICITAMENTE LO CONTRARIO.
    // for(int i=0; i<NUMERO_DE_DATOS_ARBITRARIO; i++){
    // datos.add(funcion_que_me_de_los_cosos_de_pago(id_sede,
    // fecha_que_necesito_arbitrariamente));
    // }
    // return datos.toArray();
    return prueba;
  }

  // TODO @WINJA
  public static Number[] getPaquetesEnviados(Integer sede) {
    return null;
  }

  public static Number[] getServicioSoliticado(Integer sede) {
    return null;
  }

  public static Number[] ventas(Integer sede, Integer intervalos) {
    // 0 - Dias
    // 1 - Semanas
    // 2 - Meses
    // 3 - Años
    return null;
  }

}
