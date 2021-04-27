package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.jooq.Record;

import utilities.Globals;

public class Sedes {

    public static void createSede(String nombre, String direccion) {
        Globals.db().insertInto(DSL.table("sede"), DSL.field("\"Nombre\""), DSL.field("\"Direccion\""))
                .values(nombre, direccion).execute();
    }

    public static ArrayList<Sede> getSedes() {
        String sql = "select * from sede;";
        Result<Record> resultSet = Globals.db().fetch(sql);

        ArrayList<Sede> sedes = new ArrayList<>();
        for (Record rs : resultSet) {
            int id = (int) rs.get("ID_Sede");
            String name = (String) rs.get("Nombre");
            String dir = (String) rs.get("Direccion");
            Sede sede = new Sede(id, name, dir);
            sedes.add(sede);
        }

        return sedes;
    }

}
