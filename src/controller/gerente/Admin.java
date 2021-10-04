package controller.gerente;

import javax.swing.JOptionPane;

import controller.gerente.usuarios.UserConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.Globals;
import utilities.View;

//TODO Documentar y cambiar métodos visuales de Globals a View.
/**
 * Controlador de la vista usada para el Admin
 * 
 * @author ?
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
    Globals.viewPane = content;
    labelNameUser.setText("Bienvenido " + userName);
  }

  @FXML
  void goToSedeConsulta(ActionEvent event) {
    Globals.cambiarVista("sede.consulta");
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    View.newView("user.consulta", new UserConsulta());
  }

  @FXML
  void goToAdminReportes(ActionEvent event) {
    Globals.cambiarVista("reportes", new Reportes());
  }

  @FXML
  void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
