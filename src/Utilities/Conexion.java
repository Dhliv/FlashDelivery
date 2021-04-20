package utilities;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
    private static Connection con = null;

    public static Connection startConnection() {
        try {
            if (con == null) {
                // Determina cuando se termina el programa
                // Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
                // Recupera los parámetros de conexión del archivo
                // jdbc.properties

                // ResourceBundle resource = ResourceBundle.getBundle("servicios.jdbc");
                String driver = "org.postgresql.Driver";
                // resource.getString("driver");
                String url = "jdbc:postgresql://ec2-52-87-107-83.compute-1.amazonaws.com:5432/d622m7j3h054ts";
                // resource.getString("url");
                String usr = "ikwnggozhnxhvp";
                String pwd = "a933d68a3c21b7b24a2e05104117b487091c7b880a72fe25f4ae721fadbbae9a";

                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.toString());
        }
        return con;
    }

    public static void closeConnection() {
        con = null;
    }

    static class MiShDwnHook extends Thread {
        // Justo antes de finaliza el programa la JVM invocará
        // este método donde podemos cerra la conexión
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
}
