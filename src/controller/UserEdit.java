package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import utilities.LoadView;

public class UserEdit {

  private AnchorPane content;
  private Object controladorAnterior;
  private LoadView vista;
  public UserEdit(AnchorPane contenido, Object controlador) {
    content = contenido;
    controladorAnterior = controlador;
    vista = new LoadView();
  }

  @FXML
    void goToUsuariosConsulta(ActionEvent event) {

    }

    @FXML
    void goToUsuariosRegistro(ActionEvent event) {

    }
}
