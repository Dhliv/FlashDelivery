package utilities;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoadView {

  public Parent loadView(String name, Object control) {
    var loader = new FXMLLoader(getClass().getResource("../view/" + name + ".fxml"));
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
