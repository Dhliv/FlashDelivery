package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import utilities.LoadView;

public class UserConsulta implements Initializable {

  private AnchorPane content;
  private Parent userRegister;
  private LoadView vista;

  @FXML
  private TableView<?> tableUsers;

  public UserConsulta(AnchorPane cont) {
    content = cont;
    vista = new LoadView();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub

  }

  @FXML
  void borrar(ActionEvent event) {

  }

  @FXML
  void goToUsuariosRegistro(ActionEvent event) {
    content.getChildren().clear();
    userRegister = vista.loadView("user.register", new UserRegister(content, this));
    content.getChildren().addAll(userRegister);
  }
}
