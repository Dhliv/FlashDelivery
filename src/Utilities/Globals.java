package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import controller.Login;

public class Globals {
    private static Object referenceObject;
    public static Pane viewPane;
    public static Stage pantalla;
    public static Empleado empleado;
    private static Ventana ventana;
    private static Connection conn;
    private static Map<String, Parent> views;
    private static model.RegistrarEnvio envio;

    public static void init(Object obj) {
        referenceObject = obj;
        views = new HashMap<String, Parent>();
    }

    public static void setEnvio(model.RegistrarEnvio r) {
        envio = r;
    }

    public static model.RegistrarEnvio getEnvio() {
        return envio;
    }

    public static void cambiarVista(String name) {
        cambiarVista(name, null);
    }

    public static void cambiarVista(String name, Object control) {
        if (views.get(name) == null) {
            Parent view = loadView(name, control);
            views.put(name, view);
            cambiarVista(view);
        } else {
            Parent t = views.get(name);
            cambiarVista(t);
        }
    }

    public static void cambiarVista(Parent view) {
        viewPane.getChildren().clear();
        viewPane.getChildren().add(view);
    }

    public static void clearViews() {
        views.clear();
    }

    public static Parent loadView(String name) {
        return loadView(name, null);
    }

    /**
     * Carga vista desde FXML
     * 
     * @param name    filename
     * @param control control a asignar a la vista
     * @return la vista
     */
    public static Parent loadView(String name, Object control) {
        FXMLLoader loader = new FXMLLoader(referenceObject.getClass().getResource("view/" + name + ".fxml"));
        if (control != null)
            loader.setController(control);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public static Scene loadScene(String name) {
        return new Scene(loadView(name, null));
    }

    // #---------------------------------------------------------------------------
    // # Base de Datos
    // #---------------------------------------------------------------------------

    public static DSLContext db() {

        try {
            Properties dbs = new Properties();
            dbs.load(new FileReader("src\\resources\\db.properties"));
            String url = dbs.getProperty("url");
            String usr = dbs.getProperty("usr");
            String pwd = dbs.getProperty("pwd");
            conn = DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    /**
     * Desconecta al usuario actual del sistema.
     */
    public static void logOut() {
        pantalla.close();
        ventana = new Ventana("login", new Login());
        try {
            ventana.start(pantalla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
