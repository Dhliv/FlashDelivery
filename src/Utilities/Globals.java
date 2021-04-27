package utilities;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Sedes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import controller.Login;

public class Globals {
    private static Object referenceObject;
    public static Pane adminViewPane;
    public static Stage pantalla;
    private static Ventana ventana;
    private static List<model.Sedes.Sede> sedes;

    public static void init(Object obj) {
        referenceObject = obj;
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

    public static DSLContext db() {
        String url = "jdbc:postgresql://ec2-52-87-107-83.compute-1.amazonaws.com:5432/d622m7j3h054ts";
        String usr = "ikwnggozhnxhvp";
        String pwd = "a933d68a3c21b7b24a2e05104117b487091c7b880a72fe25f4ae721fadbbae9a";
        try {
            Connection conn = DriverManager.getConnection(url, usr, pwd);
            return DSL.using(conn, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getSedes() {
        sedes = model.Sedes.getSedes();
        ArrayList<String> idSedes = new ArrayList<>();

        for (int i = 0; i < sedes.size(); i++) {
            idSedes.add(sedes.get(i).ID_Sede + " - " + sedes.get(i).nombre);
        }
        return idSedes;
    }

    public static int getIdSede(String name) {
        for (int i = 0; i < sedes.size(); i++) {
            if (name.equals(sedes.get(i).nombre))
                return sedes.get(i).ID_Sede;
        }
        return -1; // Nunca llega aquí, pero me pedía el return Xd
    }

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
