package model;

import org.jooq.Result;
import org.jooq.impl.*;

import java.util.List;

import org.jooq.Record;

import utilities.Globals;

public class Sedes {

    public static class Sede {
        public int ID_Sede;
        public String nombre;
        public String direccion;
    }

    public static void createSede(String nombre, String direccion) {
        Globals.db().insertInto(DSL.table("sede"), DSL.field("\"Nombre\""), DSL.field("\"Direccion\""))
                .values(nombre, direccion).execute();
    }

    public static List<Sede> getSedes() {
        String sql = "select * from sede;";
        Result<Record> resultSet = Globals.db().fetch(sql); // Ejecuto la query 'sql'.

        List<Sede> sedes = resultSet.into(Sede.class);

        return sedes;
    }

}
