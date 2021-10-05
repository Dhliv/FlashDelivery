package model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Conexion;

/**
 * Clase Petición. Contiene parámetros relacionados a la petición de recogida. Posee métodos para la
 * comunicación con la BD.
 * 
 * @author David Henao
 * @author Julián Orejuela
 * @version 1.0, 25/09/2021
 */
public class Peticion {
  public int id;
  public String descripcion, id_empleado, estado, cliente_solicitud, cliente_destinatario;

  /**
   * Inserta una nueva factura en la base de datos
   * @param costo    del envío
   * @param id_envio de la factura.
   */
  public static void createPeticion(String descripcion, String empleado, String remitente, String destinatario) {
    // Conexion.db().insertInto(DSL.table("peticion_recogida"), DSL.field("descripcion"),
    // DSL.field("id_empleado"), DSL.field("estado"), DSL.field("cliente_solicitud"),
    // DSL.field("cliente_destinatario"))
    // .values(descripcion, empleado, "Pendiente", remitente, destinatario).execute();
    String sql = "insert into peticion_recogida(descripcion, id_empleado, estado, cliente_solicitud, cliente_destinatario) values('" + descripcion + "', '" + empleado + "', 'Pendiente', '" + remitente + "', '" + destinatario + "')";
    Conexion.db().execute(sql);
    Conexion.closeConnection();
  }

  /**
   * Retorna una lista con todas las peticiones de recogida
   * @return List<Peticion>
   */
  public static List<Peticion> getPeticiones() {
    List<Peticion> peticiones = Conexion.db().select().from("peticion_recogida").fetch().into(Peticion.class);
    Conexion.closeConnection();
    return peticiones;
  }

  /**
   * Retorna una lista con las peticiones del empleado con cedula {@code id_empleado} y que esten en
   * estado 'Pendiente', formateadas para ser presentadas en una TableView
   * @param id_empleado cedula del empleado
   * @return ObservableList<FormattedData>
   */
  public static ObservableList<FormattedData> getPeticionesFormattedData(String id_empleado) {
    /*List<FormattedData> peticiones = Conexion.db().select(DSL.field("id"), DSL.field("descripcion"), DSL.field("nombre"), DSL.field("ciudad"), DSL.field("direccion"), DSL.field("telefono")).from("peticion_recogida").join("cliente")
        .on(DSL.field("cedula").eq(DSL.field("cliente_solicitud"))).where(DSL.field("id_empleado").eq(id_empleado)).and(DSL.field("estado").eq(2)).fetch().into(FormattedData.class);
    Conexion.closeConnection();*/
    List<FormattedData> peticiones = Conexion.db()
        .fetch("select id, descripcion, nombre, ciudad, direccion, telefono from peticion_recogida join cliente on cedula=cliente_solicitud where id_empleado='" + id_empleado + "' and estado='Pendiente'").into(FormattedData.class);
    return FXCollections.observableArrayList(peticiones);
  }

  /**
   * Clase encargada de representar los datos formateados de una Peticion para una TableView
   * @author Julián Orejuela
   */
  public static class FormattedData {
    public Integer id;
    public String descripcion, nombre, ciudad, direccion, telefono;

    public Integer getId() {
      return id;
    }

    public String getDescripcion() {
      return descripcion;
    }

    public String getNombre() {
      return nombre;
    }

    public String getCiudad() {
      return ciudad;
    }

    public String getDireccion() {
      return direccion;
    }

    public String getTelefono() {
      return telefono;
    }
  }

}
