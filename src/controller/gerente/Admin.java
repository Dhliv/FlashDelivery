package controller.gerente;

import javax.swing.JOptionPane;

import controller.gerente.usuarios.UserConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.Globals;

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
    Globals.viewPane = content;
    labelNameUser.setText("Bienvenido " + userName);
  }

  @FXML
  void goToSedeConsulta(ActionEvent event) {
    Globals.cambiarVista("sede.consulta");
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {
    Globals.cambiarVista("user.consulta", new UserConsulta());
  }

  @FXML
  void goToAdminReportes(ActionEvent event) {
    Globals.cambiarVista("reportes");
  }

  // TODO eliminar usuarios.
  @FXML
  void borrar(ActionEvent event) {
    String user = "juanito";
    JOptionPane.showOptionDialog(null, "Desea borrar a " + user + "?", "Eliminar registro", 0, 0, null, null, user);
  }

  @FXML
  void logOut(ActionEvent event) {
    Globals.logOut();
  }
}
