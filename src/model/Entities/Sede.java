package model.Entities;

import org.jooq.Field;
import org.jooq.impl.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para la entidad Sede
 * 
 * @author Julián Orejuela
 * @version 2.0, 21/09/2021
 */
public class Sede {
  public Integer id;
  public String nombre;
  public String direccion;

  /**
   * Registrar una nueva sede.
   */
  public static void upsert(Sede sede, String nombre, String direccion) {
    if (sede == null)
      Conexion.db().insertInto(DSL.table("sede"), DSL.field("nombre"), DSL.field("direccion")).values(nombre, direccion)
          .execute();
    else
      Conexion.db().update(DSL.table("sede")).set(DSL.field("nombre"), nombre).set(DSL.field("direccion"), direccion)
          .where(DSL.field("id").eq(sede.id)).execute();
    Conexion.closeConnection();
  }

  public static ObservableList<Sede> getSedesList() {
    return FXCollections.observableArrayList(getSedes());
  }

  /**
   * Obtiene una lista con todas las sedes.
   */
  public static List<Sede> getSedes() {
    List<Sede> sedes = Conexion.db().select().from("sede").fetch().into(Sede.class); // Ejecuto la query 'sql'.
    Conexion.closeConnection();
    return sedes;
  }

  /**
   * Obtiene las sedes existentes en la BD y las retorna.
   * 
   * @return Formatea el id y el nombre de la sede y retorna su respectivo
   *         listado.
   */
  public static ArrayList<String> getSedesParsed() {
    List<Sede> sedes = getSedes();
    ArrayList<String> idSedes = new ArrayList<>();

    for (int i = 0; i < sedes.size(); i++) {
      idSedes.add(sedes.get(i).id + " - " + sedes.get(i).nombre);
    }
    return idSedes;
  }

  /**
   * Obtiene el id de una sede respecto a su nombre.
   * 
   * @param name nombre de la sede.
   * @return id de la sede.
   */
  public static int getIdSede(String name) {
    String idAux = "";
    for (int i = 0; i < name.length(); i++) {
      if (Character.isWhitespace(name.charAt(i)))
        break;
      idAux += name.charAt(i);
    }
    return Integer.parseInt(idAux);
  }

  /**
   * Transforma el entero que representa el id de una sede a la id de la sede mas
   * su nombre para mostrarlo en la interfaz.
   * 
   * @param idSede id de la sede.
   * @return string con el id y nombre de la sede.
   */
  public static String parseSede(int idSede) {
    List<Sede> sedes = model.Entities.Sede.getSedes();

    for (int i = 0; i < sedes.size(); i++) {
      if (idSede == sedes.get(i).id)
        return (idSede + " - " + sedes.get(i).nombre);
    }

    return "";
  }

  // #---------------------------------------------------------------------------
  // # GETTERS
  // #---------------------------------------------------------------------------

  public Integer getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDireccion() {
    return direccion;
  }
}
