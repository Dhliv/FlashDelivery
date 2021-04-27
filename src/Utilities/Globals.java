package utilities;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Globals {
    private static Object referenceObject;
    public static Pane adminViewPane;

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

}
