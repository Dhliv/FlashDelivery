package utilities;

import java.sql.*;
import javax.swing.JOptionPane;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.io.IOException;

public class Conexion {
    private static Connection conn = null;

    /**
     * Arreglo temporal para la eliminación futura de este método.
     */
    public static Connection startConnection() {
        db();
        return conn;
    }

    static class MiShDwnHook extends Thread {
        // Justo antes de finalizar el programa la JVM invocará
        // este método donde podemos cerrar la conexión
        @Override
        public void run() {
            try {
                Connection con = Conexion.startConnection();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
            }
        }
    }

    /**
     * Establece la conexión con la base de datos con jOOQ.
     */
    public static DSLContext db() {
        try {
            String url = "jdbc:postgresql://ec2-52-87-107-83.compute-1.amazonaws.com:5432/d622m7j3h054ts";
            String usr = "ikwnggozhnxhvp";
            String pwd = "a933d68a3c21b7b24a2e05104117b487091c7b880a72fe25f4ae721fadbbae9a";
            conn = DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return DSL.using(conn, SQLDialect.POSTGRES);
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn = null;
    }
}
