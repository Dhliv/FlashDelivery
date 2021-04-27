package utilities;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Globals {
    private static Object referenceObject;
    public static Pane adminViewPane;

    public static void init(Object obj) {
        referenceObject = obj;
    }

    public static Parent loadView(String name) {
        return loadView(name, null);
    }

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

}
