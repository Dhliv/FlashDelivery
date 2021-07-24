package model.Entities;

import org.jooq.impl.*;
import utilities.Conexion;

import java.util.ArrayList;
import java.util.List;

public class Sede {
    public int id;
    public String nombre;
    public String direccion;


    public static int createSede(String nombre, String direccion) {
        int query = Conexion.db().insertInto(DSL.table("sede"), DSL.field("\"Nombre\""), DSL.field("\"Direccion\"")).values(nombre, direccion).execute();
        Conexion.closeConnection();
        return query;
    }

    /**
     * Obtiene todas las sedes que existen en la base de datos.
     * 
     * @return listado de sedes.
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
            if (Character.isWhitespace(name.charAt(i))) break;
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
            if (idSede == sedes.get(i).id) return (idSede + " - " + sedes.get(i).nombre);
        }

        return "";
    }
}
