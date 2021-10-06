package controller.gerente;

import controller.gerente.usuarios.UserConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import utilities.Globals;
import utilities.View;

/**
 * Controlador de la vista usada para el Admin
 * 
 * @author Alejandro Pergueza
 * @author David Henao
 * @author Reynell Arkad Devji Quevedo
 * @version 1.0
 */
public class Admin {
  private String userName;

  public Admin(String userName) {
    this.userName = userName;
  }

  @FXML private Label labelNameUser;

  @FXML private AnchorPane content;

  @FXML private SplitPane splitPanel;
  

  @FXML private void initialize() {
    View.setViewPane(content, false);
    labelNameUser.setText("Bienvenido " + userName);
    // splitPanel.getStyleClass().add(xd.getStyle().toString());
  }

  @FXML void goToSedeConsulta(ActionEvent event) {
    View.cambiar("sede.consulta");
  }

  @FXML void goToUsuariosConsulta() {
    View.newView("user.consulta", new UserConsulta());
    /*Runnable r = () -> {
      View.newView("user.consulta", new UserConsulta());
    };
    
    Thread t = new Thread(r);
    t.start();*/
  }

  @FXML void goToAdminReportes(ActionEvent event) {
    View.cambiar("reportes", new Reportes());
  }

  @FXML void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
