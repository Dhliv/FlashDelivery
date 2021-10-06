package utilities;

import java.sql.*;
import javax.swing.JOptionPane;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import jdk.jshell.spi.ExecutionControl.ExecutionControlException;

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
      Properties dbs = new Properties();
      dbs.load(new FileReader("src/resources/db.properties"));
      String url = dbs.getProperty("url");
      String usr = dbs.getProperty("usr");
      String pwd = dbs.getProperty("pwd");
      conn = DriverManager.getConnection(url, usr, pwd);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return DSL.using(conn, SQLDialect.POSTGRES);
  }

  public static void closeConnection() {
    try {
      conn.close();
    } catch (Exception e) {
      // e.printStackTrace();
    }
    conn = null;
  }
}
