package controller.gerente;

import controller.gerente.usuarios.UserConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.Globals;
import utilities.View;

/**
 * Controlador de la vista usada para el Admin
 * 
 * @author Alejandro Pergueza
 * @author David Henao
 * @author Julián Andres Orejuela
 * @author Reynell Arkad Devji Quevedo
 * @version 1.0
 */
public class Admin {
  private String userName;

  public Admin(String userName) {
    this.userName = userName;
  }

  @FXML
  private Label labelNameUser;

  @FXML
  private AnchorPane content;

  @FXML
  private void initialize() {
    View.setViewPane(content);
    labelNameUser.setText("Bienvenido " + userName);
  }

  @FXML
  void goToSedeConsulta(ActionEvent event) {
    View.cambiar("sede.consulta");
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    View.newView("user.consulta", new UserConsulta());
  }

  @FXML
  void goToAdminReportes(ActionEvent event) {
    View.cambiar("reportes", new Reportes());
  }

  @FXML
  void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
