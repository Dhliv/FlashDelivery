package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;

import utilities.Globals;

public class Sedes {

    public static void createSede(String nombre, String direccion) {
        Globals.db().insertInto(DSL.table("sede"), DSL.field("\"Nombre\""), DSL.field("\"Direccion\""))
                .values(nombre, direccion).execute();
    }

    public static ArrayList<Sede> selectSedes() {
        String sql = "select * from sede;";
        var rs = Globals.db().fetch(sql);
        int numSedes = rs.size();

        ArrayList<Sede> sedes = new ArrayList<>();
        for (int i = 0; i < numSedes; i++) {
            int id = (int) rs.get(i).get("ID_Sede");
            String name = (String) rs.get(i).get("Nombre");
            String dir = (String) rs.get(i).get("Direccion");
            Sede sede = new Sede(id, name, dir);
            sedes.add(sede);
        }

        return sedes;
    }

}
