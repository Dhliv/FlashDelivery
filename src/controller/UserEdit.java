package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class UserEdit {

  private AnchorPane content;
  private Object controladorAnterior;

  public UserEdit(AnchorPane contenido, Object controlador) {
    content = contenido;
    controladorAnterior = controlador;
  }

  @FXML
  void goToUsuariosConsulta(ActionEvent event) {

  }

  @FXML
  void goToUsuariosRegistro(ActionEvent event) {

  }
}
